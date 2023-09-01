package com.thepan.reservationapiserver.domain.reservation.dto.page

import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

data class ReservationDateRangeRequest(
    @field:NotNull(message = "시작 범위 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val searchStartDate: Date,
    @field:NotNull(message = "끝 범위 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val searchEndDate: Date,
)
