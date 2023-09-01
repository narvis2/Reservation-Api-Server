package com.thepan.reservationapiserver.domain.reservation.dto

import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

data class ReservationSeatListRequest(
    @field:NotNull
    val timeType: String,
    @field:NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val reservationDateTime: Date,
)
