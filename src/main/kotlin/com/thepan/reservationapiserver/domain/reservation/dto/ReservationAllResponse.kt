package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import java.util.Date

data class ReservationAllResponse(
    val id: Long?,
    val name: String,
    val phoneNumber: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val reservationDateTime: Date,
    val reservationCount: Int,
    val seats: List<String>,
    val timeType: TimeType
)