package com.thepan.reservationapiserver.domain.reservation.dto.page

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ReservationDateRangeRequest(
    @field:NotNull(message = "시작 범위 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val searchStartDate: LocalDateTime,
    @field:NotNull(message = "끝 범위 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val searchEndDate: LocalDateTime,
)
