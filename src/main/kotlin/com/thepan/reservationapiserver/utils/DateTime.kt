package com.thepan.reservationapiserver.utils

import java.text.SimpleDateFormat
import java.util.Date

fun designateTime(type: String, date: Date): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateString = dateFormat.format(date)
    val formattedString = "$dateString $type"
    return SimpleDateFormat("yyyy-MM-dd HH:mm").parse(formattedString)
}

fun dateTimeFormatToDate(date: Date): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val formattedString = dateFormat.format(date)
    
    return dateFormat.parse(formattedString)
}