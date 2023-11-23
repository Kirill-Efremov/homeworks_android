package ru.kpfu.homeworks.utlis

import android.app.NotificationManager

enum class Importance(val importance: String, val id: Int) {
    Medium("Medium", NotificationManager.IMPORTANCE_LOW),
    High("High", NotificationManager.IMPORTANCE_DEFAULT),
    Urgent("Urgent", NotificationManager.IMPORTANCE_HIGH);
}