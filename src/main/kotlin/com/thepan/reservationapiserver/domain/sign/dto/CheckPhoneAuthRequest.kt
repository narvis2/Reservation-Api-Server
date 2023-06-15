package com.thepan.reservationapiserver.domain.sign.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CheckPhoneAuthRequest(
    @field:NotBlank(message = "예약자 성함을 입력해주세요.")
    val name: String,
    @field:NotBlank(message = "예약자 휴대폰 번호를 입력해주세요.")
    @field:Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$", message = "유효하지 않은 휴대폰 번호입니다.")
    val phoneNumber: String,
    @field:NotBlank(message = "인증번호를 입력해주세요.")
    val authenticationCode: String
)