package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import java.time.LocalDateTime

data class ReservationRangeSectionResponse(
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd",
        timezone = "Asia/Seoul"
    )
    val sectionTitle: LocalDateTime,
    val timeType: TimeType,
    val list: List<ReservationRangeSectionDataResponse>
)
