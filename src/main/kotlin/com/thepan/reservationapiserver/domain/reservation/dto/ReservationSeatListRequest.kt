package com.thepan.reservationapiserver.domain.reservation.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationSeatListRequest(
    @field:NotNull
    val timeType: String,
    val reservationDateTime: LocalDateTime,
)
