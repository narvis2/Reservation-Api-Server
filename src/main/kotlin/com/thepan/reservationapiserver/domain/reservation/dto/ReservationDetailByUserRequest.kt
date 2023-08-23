package com.thepan.reservationapiserver.domain.reservation.dto

import jakarta.validation.constraints.NotBlank

data class ReservationDetailByUserRequest(
    @field:NotBlank(message = "발급 받으신 예약 번호를 입력해주세요.")
    val certificationNumber: String
)
