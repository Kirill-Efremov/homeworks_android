package ru.kpfu.homewroks

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.homeworks.R
import ru.kpfu.homewroks.ui.BookFragment
import ru.kpfu.homewroks.ui.StartFragment


class MainActivity : AppCompatActivity() {
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







