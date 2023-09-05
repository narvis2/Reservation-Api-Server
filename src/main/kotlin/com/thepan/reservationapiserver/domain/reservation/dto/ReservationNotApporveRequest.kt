package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationNotApporveRequest (
    @field:NotNull
    val timeType: TimeType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val reservationDateTime: LocalDateTime,
)