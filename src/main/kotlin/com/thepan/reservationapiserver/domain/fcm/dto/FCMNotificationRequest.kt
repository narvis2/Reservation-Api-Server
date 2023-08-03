package com.thepan.reservationapiserver.domain.fcm.dto

data class FCMNotificationRequest(
    val targetId: Long?,
    val title: String,
    val body: String,
    val data: Map<String, String>?,
)
