package com.thepan.reservationapiserver.domain.reservation.dto

import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

data class ReservationNotApporveRequest (
    @field:NotNull
    val timeType: TimeType,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val reservationDateTime: Date,
)