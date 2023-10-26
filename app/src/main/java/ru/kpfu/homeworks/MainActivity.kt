package ru.kpfu.homeworks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val fragmentContainerId: Int = R.id.main_activity_container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, StartFragment())
            .commit()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
