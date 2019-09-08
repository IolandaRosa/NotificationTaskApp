package com.example.notificationsapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateToStringDate(locale: Locale = Locale.getDefault()): String {

    val format = SimpleDateFormat("dd-MM-yyyy", locale)
    return format.format(Calendar.getInstance().time)
}

fun timeToStringTime(locale: Locale = Locale.getDefault()): String {

    val format = SimpleDateFormat("hh:mm", locale)
    return format.format(Calendar.getInstance().time)
}