package ru.kpfu.homeworks.ui

import android.app.Notification
import android.app.NotificationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentSettingsNotificationsBinding
import ru.kpfu.homeworks.utlis.Importance
import ru.kpfu.homeworks.utlis.NotificationBuilder
import ru.kpfu.homeworks.utlis.Visibility

class SettingsNotificationsFragment : Fragment() {
    private var _viewBinding: FragmentSettingsNotificationsBinding? = null
    private val viewBinding: FragmentSettingsNotificationsBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSettingsNotificationsBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        saveSettings()
    }

    private fun initViews() {
        with(viewBinding) {
            cbDetailedMessage.setOnCheckedChangeListener { _, isChecked ->
                NotificationBuilder.detailedMessage = isChecked
            }
            cbDisplayButtons.setOnCheckedChangeListener { _, isChecked ->
                NotificationBuilder.displayButtons = isChecked
            }
            rgImportance.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    (R.id.rb_urgent) -> {
                        NotificationBuilder.importance =
                            Importance.Urgent
                    }

                    (R.id.rb_high) -> {
                        NotificationBuilder.importance =
                            Importance.High
                    }

                    (R.id.rb_medium) -> {
                        NotificationBuilder.importance =
                            Importance.Medium
                    }

                    else -> Snackbar.make(
                        root,
                        "Error",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            rgVisibility.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    (R.id.rb_private) -> {
                        NotificationBuilder.visibility =
                            Visibility.Private
                    }

                    (R.id.rb_public) -> {
                        NotificationBuilder.visibility =
                            Visibility.Public
                    }

                    (R.id.rb_secret) -> {
                        NotificationBuilder.visibility =
                            Visibility.Secret
                    }

                    else -> Snackbar.make(
                        root,
                        "Error",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }


    }

    private fun saveSettings() {
        with(viewBinding) {
            cbDetailedMessage.isChecked = NotificationBuilder.detailedMessage
            cbDisplayButtons.isChecked = NotificationBuilder.displayButtons
            when (NotificationBuilder.importance.id) {
                NotificationManager.IMPORTANCE_DEFAULT -> {
                    rbHigh.isChecked = true
                }

                NotificationManager.IMPORTANCE_HIGH -> {
                    rbUrgent.isChecked = true
                }

                else -> {
                    rbMedium.isChecked = true
                }
            }
            when (NotificationBuilder.visibility.id) {
                Notification.VISIBILITY_PUBLIC -> {
                    rbPublic.isChecked = true
                }

                Notification.VISIBILITY_PRIVATE -> {
                    rbPrivate.isChecked = true
                }

                else -> {
                    rbSecret.isChecked = true
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}