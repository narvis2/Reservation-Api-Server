package com.thepan.reservationapiserver.domain.reservation.dto

import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationNotApporveRequest (
    @field:NotNull
    val timeType: TimeType,
    val reservationDateTime: LocalDateTime,
)