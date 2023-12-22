package ru.itis.homework.di

import ru.itis.homework.data.db.InceptionDatabase
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room

object ServiceLocator {

    private var dbInstance: InceptionDatabase? = null

    private var inceptionPref: SharedPreferences? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, "inception.db")
            .build()

        inceptionPref = ctx.getSharedPreferences("inception_pref", Context.MODE_PRIVATE)
    }

    fun getDbInstance(): InceptionDatabase {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }

    fun getSharedPreferences(): SharedPreferences {
        return inceptionPref ?: throw IllegalStateException("Preferences not initialized")
    }
}