package ru.kpfu.homeworks.utlis

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

import ru.kpfu.homeworks.MainActivity
import ru.kpfu.homeworks.R


object NotificationBuilder {
    var importance = Importance.High
    var visibility = Visibility.Public
    var detailedMessage: Boolean = false
    var displayButtons: Boolean = false

    fun sendNotification(ctx: Context, title: String, text: String) {
        val channelId = getNotificationChannelId(importance)
        val builder = NotificationCompat.Builder(
            ctx,
            channelId,
        ).setSmallIcon(R.drawable.baseline_message_24)
            .setContentTitle(title)
            .setContentText(text)
            .setVisibility(visibility.id)
            .setPublicVersion(
                NotificationCompat.Builder(
                    ctx,
                    channelId
                )
                    .setSmallIcon(R.drawable.baseline_message_24)
                    .setContentTitle(title)
                    .build()
            )
        if (detailedMessage) {
            builder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(text)
            )
        }
        if (displayButtons) {
            val makeToastIntent = Intent(ctx, MainActivity::class.java)
            makeToastIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            makeToastIntent.putExtra("action", MainActivity.ACTION_MAKE_TOAST)
            val pendingIntentToast = PendingIntent.getActivity(
                ctx,
                99,
                makeToastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            builder.addAction(
                NotificationCompat.Action(
                    null,
                    "Make Toast",
                    pendingIntentToast
                )
            )

            val openSettingsIntent = Intent(ctx, MainActivity::class.java)
            openSettingsIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            openSettingsIntent.putExtra("action", MainActivity.ACTION_OPEN_SETTINGS)
            val pendingIntentSettings = PendingIntent.getActivity(
                ctx,
                88,
                openSettingsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            builder.addAction(
                NotificationCompat.Action(
                    null,
                    "Open Settings",
                    pendingIntentSettings
                )
            )
        }

        val intent = Intent(ctx, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(
             ctx,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        builder.setContentIntent(pIntent)

        val notificationManager = ctx
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123, builder.build())

    }

    fun createNotificationChannels(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            for (importance in Importance.values()) {
                NotificationChannel(
                    getNotificationChannelId(importance),
                    importance.importance,
                    importance.id,
                ).also {
                    notificationManager.createNotificationChannel(it)
                }
            }
        }

    }
    private fun getNotificationChannelId(importance: Importance) = importance.name
}