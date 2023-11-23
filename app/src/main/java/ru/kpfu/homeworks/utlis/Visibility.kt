package ru.kpfu.homeworks.utlis

import android.app.Notification

enum class Visibility(  val id: Int) {
    Public( Notification.VISIBILITY_PUBLIC),
    Secret( Notification.VISIBILITY_SECRET),
    Private( Notification.VISIBILITY_PRIVATE);
}