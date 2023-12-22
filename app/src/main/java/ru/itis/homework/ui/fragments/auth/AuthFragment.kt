package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kpfu.itis.android_inception_23.utils.ParamsKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.MainActivity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.utils.PasswordEncoder
import ru.itis.homework.utils.UserSession

import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val viewBinding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity?
        activity?.hideBottomNavigation()
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            registrBtn.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, RegistrationFragment())
                    .commit()
            }
            actionBtn.setOnClickListener {
                val email = emailEt.text.toString()
                val password = passwordEt.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Все поля должны быть заполнены",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    lifecycleScope.launch(Dispatchers.IO) {
                        val user =
                            ServiceLocator.getDbInstance().userDao.getUser(
                                email,
                                PasswordEncoder.encoder(password)
                            )
                        if (user == null) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    "Такого пользователя не существует",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            if (user.deletionDate > 0) {
                                if (checkDeleteUser(user.deletionDate)) {
                                    withContext(Dispatchers.Main) {
                                        showDialog(user.id)
                                    }
                                } else {
                                    ServiceLocator.getDbInstance().userDao.deleteUserByIdQuery(user.id)
                                }
                            } else {
                                UserSession.setUserId(user.id)
                                parentFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.main_activity_container, ProfileFragment())
                                    .commit()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showDialog(userId: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Аккаунт был удален")
        builder
            .setNegativeButton("Удалить аккаунт полностью") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao.deleteUserByIdQuery(userId)
                }
            }
            .setPositiveButton("Восстановить") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao.updateDeleteDate(userId, 0)
                }
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, ProfileFragment())
                    .commit()
                ServiceLocator.getSharedPreferences().edit().putInt(ParamsKey.USER_ID, userId)
                    .apply()
            }.create().show()
    }

    private fun checkDeleteUser(deleteDate: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val differentTime =
            (currentTime - deleteDate) / (24 * 60 * 60 * 1000)
        return differentTime < 7
    }
}