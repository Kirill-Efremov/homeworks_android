package ru.itis.homework

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.ui.fragments.auth.AddFilmFragment
import ru.itis.homework.ui.fragments.auth.MainFragment
import ru.itis.homework.ui.fragments.auth.ProfileFragment
import ru.itis.homework.ui.fragments.auth.RegistrationFragment
import ru.kpfu.homeworks.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_container, RegistrationFragment(),)
            .commit()
        initInstances()

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> {
                    loadFragment(MainFragment())
                    true
                }
                R.id.profileFragment -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.addFilmFragment -> {
                    loadFragment(AddFilmFragment())
                    true
                }

                else -> { true }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_container,fragment)
        transaction.commit()
    }
    fun hideBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.main_bottom_navigation).visibility = View.GONE
    }

    private fun initInstances() {
        ServiceLocator.initData(ctx = this)
    }
}