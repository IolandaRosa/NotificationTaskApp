package com.example.notificationsapp.handlers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.notificationsapp.R
import com.example.notificationsapp.ui.MainActivity

class NotificationHandler(var context: Context) {

    private val notificationMagager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val NOTIFICATION_ID = 0
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }


    fun createNotificationChannel() {

        //Cria canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notification every 15 minutes"
                enableLights(true)
                enableVibration(true)
                lightColor = Color.RED
            }

            notificationMagager.createNotificationChannel(channel)
        }
    }

    fun deliveryNotification() {

        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Construtor notificação
        var builder = NotificationCompat.Builder(
            context,
            PRIMARY_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Registar Tarefa")
            .setContentText("Hora de registar o que esta a fazer")
            //.setStyle(NotificationCompat.BigTextStyle().bigText("Notification Description is longer and can not feet in one line..."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            //Coloca intente que dispara quando aparece a notificação
            .setContentIntent(pendingIntent)

        notificationMagager.notify(NOTIFICATION_ID, builder.build())
    }

}