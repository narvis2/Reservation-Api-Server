package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationStatusCondition(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @field:NotNull(message = "조회할 날짜를 넣어주세요.")
    val dateTime: LocalDateTime
)