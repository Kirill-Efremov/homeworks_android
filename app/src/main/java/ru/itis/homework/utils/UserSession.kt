package ru.itis.homework.utils

import com.kpfu.itis.android_inception_23.utils.ParamsKey
import ru.itis.homework.di.ServiceLocator

object UserSession {
    fun getUserId():Int{
        return  ServiceLocator.getSharedPreferences().getInt(ParamsKey.USER_ID, -1)
    }
    fun deleteUserId(){
        return  ServiceLocator.getSharedPreferences().edit().remove(ParamsKey.USER_ID).apply()
    }
    fun setUserId(id:Int){
        return   ServiceLocator.getSharedPreferences().edit()
            .putInt(ParamsKey.USER_ID, id).apply()
    }
}