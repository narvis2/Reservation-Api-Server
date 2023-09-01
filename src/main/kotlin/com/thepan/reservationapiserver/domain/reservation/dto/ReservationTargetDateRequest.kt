package com.thepan.reservationapiserver.domain.reservation.dto

import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class ReservationTargetDateRequest(
    @field:NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val findDate: Date
)
