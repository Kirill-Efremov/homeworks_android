package ru.kpfu.homeworks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity( ) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_container, StartFragment(),"ahujkhghjsgdaS")
                .commit()
        }
    }
}