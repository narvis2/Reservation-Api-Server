package com.thepan.reservationapiserver.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun designateTime(type: String, date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = date.format(formatter)
    return "$formattedDate $type"
}

fun formattedDate(type: String, date: LocalDateTime): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = date.format(formatter)
    val dateString =  "$formattedDate ${type}:00"
    val formatterResult = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    
    return LocalDateTime.parse(dateString, formatterResult)
}