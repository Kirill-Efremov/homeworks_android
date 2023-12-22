package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kpfu.itis.android_inception_23.utils.ParamsKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.homework.MainActivity
import ru.itis.homework.data.db.dao.UserDao
import ru.itis.homework.data.db.entity.UserEntity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.model.UserModel
import ru.itis.homework.utils.PhoneTextWatcher
import ru.itis.homework.utils.RegexUtil
import ru.itis.homework.utils.UserMapper
import ru.itis.homework.utils.UserSession
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var _viewBinding: FragmentRegistrationBinding? = null
    private val viewBinding: FragmentRegistrationBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRegistrationBinding.inflate(inflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val activity = activity as MainActivity?
        activity?.hideBottomNavigation()
    }


    private fun initViews() {
        val id = UserSession.getUserId()
        if (id != -1) {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, MainFragment())
                .commit()
        }
        with(viewBinding) {
            phoneEt.addTextChangedListener(PhoneTextWatcher(phoneEt))
            loginBtn.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, AuthFragment())
                    .commit()
            }

            actionBtn.setOnClickListener {
                val name = usernameEt.text.toString()
                val password = passwordEt.text.toString()
                val email = emailEt.text.toString()
                val phone = phoneEt.text.toString()
                if ((name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty())) {
                    makeToast("Все поля должны быть заполнены")
                } else {
                    lifecycleScope.launch(Dispatchers.IO) { // нельзя обращаться к бд в главном потоке
                        val userByPhone = (ServiceLocator.getDbInstance().userDao.getUserByPhone(phone))
                        val userByEmail = ServiceLocator.getDbInstance().userDao.getUserByEmail(email)
                        when {
                            !RegexUtil.isValidPhone(phone) -> makeToast("Номер телефона введен неверно")
                            !RegexUtil.isValidEmail(email) -> makeToast("Почта введена неверно")
                            userByPhone != null -> makeToast("Номер уже существует")
                            userByEmail != null -> makeToast("Почта уже существует")
                            else -> {
                                makeToast("Пользователь зарегестрирован успешно")
                                val newUser = UserModel(
                                    id = 0,
                                    name = name,
                                    phoneNumber = phone,
                                    emailAddress = email,
                                    password = password,
                                    deleteDate = 0
                                )
                                ServiceLocator.getDbInstance().userDao.addUser(
                                    UserMapper.userEntityFromUserModel(
                                        newUser
                                    )
                                )
                                parentFragmentManager
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.main_activity_container, AuthFragment())
                                    .commit()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun makeToast(text: String) {
        activity?.runOnUiThread {
            Toast.makeText(
                context, text, Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}