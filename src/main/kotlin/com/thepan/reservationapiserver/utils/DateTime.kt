package com.thepan.reservationapiserver.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun designateTime(type: String, date: LocalDateTime): LocalDateTime {
    val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val formattedString = "${date.toLocalDate()}T$type"
    return LocalDateTime.parse(formattedString, timeFormatter)
}