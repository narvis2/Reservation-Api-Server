package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import java.time.LocalDateTime

data class ReservationDetailResponse(
    val id: Long?,
    val name: String, // 성함
    val phoneNumber: String, // 핸드폰 번호
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val reservationDateTime: LocalDateTime, // 예약 시간
    val reservationCount: Int, // 예약 인원 수
    val isTermAllAgree: Boolean, // 약관 동의 여부
    val isUserValidation: Boolean, // 본인 인증 여부
    val certificationNumber: String?, // 예약 인증 번호
    val seats: List<SeatType> // 예약한 좌석
)