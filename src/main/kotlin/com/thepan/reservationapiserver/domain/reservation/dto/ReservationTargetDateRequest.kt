package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationTargetDateRequest(
    @field:NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val findDate: LocalDateTime
)
