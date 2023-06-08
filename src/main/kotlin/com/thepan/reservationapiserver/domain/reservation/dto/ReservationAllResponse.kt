package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import java.time.LocalDateTime

data class ReservationAllResponse(
    val id: Long?,
    val name: String,
    val phoneNumber: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val reservationDateTime: LocalDateTime,
    val reservationCount: Int,
    val seat: List<Seat>
)