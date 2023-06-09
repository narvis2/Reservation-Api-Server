package com.thepan.reservationapiserver.domain.sign.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @field: Email(message = "이메일 형식을 맞춰주세요.")
    @field: NotBlank(message = "이메일을 입력해주세요.")
    val email: String,
    @field: NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String
)
