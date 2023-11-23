package ru.kpfu.homeworks

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.homeworks.databinding.ActivityMainBinding
import ru.kpfu.homeworks.utlis.NotificationBuilder


class MainActivity : AppCompatActivity() {
    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!
    private var job: Job? = null
    private var completedCoroutines = 0
    private var maxCoroutines = 0
    private var stopOnBackground = false
    private val airplaneModeReceiver = AirplaneModeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val navController =
            (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
                .navController

        NavigationUI.setupWithNavController(viewBinding.bnvMain, navController, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestNotificationsPermissionWithRationale()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationBuilder.createNotificationChannels(this)
        }
        val action = intent.getIntExtra("action", ACTION_NO)
        onIntentAction(action)

        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action = intent?.getIntExtra("action", ACTION_NO) ?: ACTION_NO
        onIntentAction(action)
    }

    private fun onIntentAction(action: Int) {
        when (action) {
            ACTION_MAKE_TOAST -> {
                Toast
                    .makeText(
                        this,
                        getString(R.string.toast_message),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            ACTION_OPEN_SETTINGS -> {
                (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
                    .navController
                    .apply {
                        if (currentDestination?.id != R.id.settingsNotificationsFragmentFragment) {
                            popBackStack(R.id.startFragment, false)
                            navigate(R.id.action_mainFragment_to_notificationSettingsFragment)
                        }
                    }
            }
        }

    }
    private suspend fun startCoroutine(index: Int, total: Int) {
        delay(2000)
        Log.e(javaClass.name, "Coroutine $index out of  $total finished")
        completedCoroutines++
    }
    fun startAllCoroutines(n: Int, async: Boolean, stopOnBackground: Boolean) {
        maxCoroutines = n
        this.stopOnBackground = stopOnBackground
        job = lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                repeat(n) {
                    if (async) {
                        launch { startCoroutine(it + 1, n) }
                    } else {
                        startCoroutine(it + 1, n)
                    }
                }
            }
        }.also {
            it.invokeOnCompletion { cause ->
                if (cause == null) {
                    NotificationBuilder.sendNotification(this, getString(R.string.coroutine_text), getString(R.string.job_finish))
                } else if (cause is CancellationException) {
                    Log.e(javaClass.name, "cancelled $completedCoroutines coroutines  ")
                }
                job = null
            }
        }
    }
    override fun onStop() {
        if (stopOnBackground) {
            job?.cancel(
                getString(R.string.app_turned_off)
            )
        }
        super.onStop()
    }


    private fun requestNotificationsPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
            Snackbar.make(
                viewBinding.root,
                R.string.remove_notifications_through_settings,
                                Snackbar.LENGTH_LONG
            ).setAction(R.string.permit_text) { requestNotificationsPermission() }
                .show()
        } else {
            requestNotificationsPermission()
        }
    }
    private fun requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var accept = false
        when (requestCode) {
            NOTIFICATION_PERMISSION_REQUEST_CODE -> {
                accept = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }
        if (!accept) requestOpenApplicationSettings()
    }

    private fun requestOpenApplicationSettings() {
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.permit_text)
            .setMessage(R.string.permit_notifications_settings)
            .setPositiveButton(R.string.open_settings) { _, _ ->
                openApplicationSettings()
            }
        builder.create().show()
    }
    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(appSettingsIntent, NOTIFICATION_PERMISSION_REQUEST_CODE)
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
        unregisterReceiver(airplaneModeReceiver)
    }
    inner class AirplaneModeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                val isAirplaneModeOn = Settings.Global.getInt(
                    context?.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON,
                    0
                ) != 0
                findViewById<View>(R.id.textView4).visibility = if (isAirplaneModeOn) View.VISIBLE else View.INVISIBLE
                findViewById<Button>(R.id.btn_send_message).isEnabled = !isAirplaneModeOn
            }
        }
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1

        const val ACTION_NO = 0
        const val ACTION_MAKE_TOAST = 1
        const val ACTION_OPEN_SETTINGS = 2
    }
}