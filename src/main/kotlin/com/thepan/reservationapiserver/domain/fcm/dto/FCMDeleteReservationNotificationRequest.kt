package com.thepan.reservationapiserver.domain.fcm.dto

data class FCMDeleteReservationNotificationRequest(
    val fcmToken: String,
    val title: String,
    val body: String,
    val data: Map<String, String>,
)
