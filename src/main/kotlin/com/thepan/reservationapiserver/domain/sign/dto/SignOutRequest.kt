package com.thepan.reservationapiserver.domain.sign.dto

import jakarta.validation.constraints.NotBlank

data class SignOutRequest(
    @field:NotBlank(message = "accessToken 이 존재하지 않습니다.")
    val accessToken: String
)
