package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.data.db.dao.UserDao
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.utils.PasswordEncoder
import ru.itis.homework.utils.PhoneTextWatcher
import ru.itis.homework.utils.RegexUtil
import ru.itis.homework.utils.UserMapper
import ru.itis.homework.utils.UserSession
import ru.kpfu.homeworks.R

import ru.kpfu.homeworks.databinding.FragmnetProfileBinding

class ProfileFragment : Fragment(R.layout.fragmnet_profile) {
    private val viewBinding by viewBinding(FragmnetProfileBinding::bind)
    private val userDao: UserDao = ServiceLocator.getDbInstance().userDao
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.visibility =
            View.VISIBLE
        initViews()

    }

    private fun initViews() {

        with(viewBinding) {
            lifecycleScope.launch(Dispatchers.IO) {
                val userFromDb = userDao.getUserInfoById(
                    UserSession.getUserId()
                )
                val user = userFromDb?.let {
                    UserMapper.userModelFromUserEntity(
                        it
                    )
                }
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        nameTv.text = user.name
                        emailTv.text = user.emailAddress
                        phoneTv.text = user.phoneNumber
                    }
                }
            }

            updateEmailBtn.setOnClickListener {
                updateEmail(emailEt.text.toString())
            }
            updatePhoneBtn.setOnClickListener {
                updatePhone(phoneEt.text.toString())
            }
            updatePasswordBtn.setOnClickListener {
                updatePassword(oldPasswordEt.text.toString(), newPasswordEt.text.toString())
            }
            phoneEt.addTextChangedListener(PhoneTextWatcher(phoneEt))

            exitBtn.setOnClickListener {
                UserSession.deleteUserId()
                parentFragmentManager.beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.main_activity_container, AuthFragment()).commit()
            }
            deleteBtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userId = UserSession.getUserId()
                    ServiceLocator.getDbInstance().userDao.setDeletionDate(userId, System.currentTimeMillis()  )
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_container, AuthFragment()).commit()
                    UserSession.deleteUserId()
                }
            }
        }
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = userDao.getUserInfoById( UserSession.getUserId())

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                makeToast("Поля не могут быть пустыми")
                return@launch
            }

            if (user != null) {
                if (!PasswordEncoder.matches(oldPassword, user.password)) {
                    println("$oldPassword - ${user.password}")
                    makeToast("Неверный старый пароль")
                    return@launch
                }
            }

            if (oldPassword == newPassword) {
                makeToast("Новый пароль должен отличаться от старого")
                return@launch
            }

            userDao.updatePassword( UserSession.getUserId(), PasswordEncoder.encoder(newPassword))
            makeToast("Пароль успешно обновлен")
        }

    }


    private fun makeToast(text: String) {
        activity?.runOnUiThread {
            Toast.makeText(
                context, text, Toast.LENGTH_SHORT
            ).show()
        }
    }



    private fun updateEmail(newEmail: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userByEmail = userDao.getUserByEmail(newEmail)
            if (!RegexUtil.isValidEmail(email = newEmail) || newEmail.isEmpty()) {
                makeToast("Ввели почту неверно")
            } else if (userByEmail != null) {
                makeToast("Пользователь с такой почтой  уже существует")
            } else {
                userDao.updateEmail( UserSession.getUserId(), newEmail)
                makeToast("Почта пользователя  успешно обновлена")
                activity?.runOnUiThread { viewBinding.emailTv.text = newEmail }
            }
        }
    }

    private fun updatePhone(newPhone: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userByPhone = userDao.getUserByPhone(newPhone)

            if (!RegexUtil.isValidPhone(newPhone) || newPhone.isEmpty()) {
                makeToast("Ввели номер телефона не верно")
            } else if (userByPhone != null) {
                makeToast("Пользователь с таким номером уже существует")
            } else {
                userDao.updatePhone( UserSession.getUserId(), newPhone)
                makeToast("Номер телефона успешно обновлен")
                activity?.runOnUiThread { viewBinding.phoneTv.text = newPhone }
            }
        }
    }

}
