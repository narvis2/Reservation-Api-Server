package com.thepan.reservationapiserver.domain.reservation.dto.page

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import java.time.LocalDateTime

data class ReservationConditionResponse(
    val id: Long?,
    val name: String,
    val phoneNumber: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val reservationDateTime: LocalDateTime,
    val reservationCount: Int,
    val timeType: TimeType,
    val certificationNumber: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime?
)
