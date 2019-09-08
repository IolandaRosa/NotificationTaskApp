package com.example.notificationsapp.handlers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notificationHandler = NotificationHandler(context)
        notificationHandler.deliveryNotification()
    }
}
