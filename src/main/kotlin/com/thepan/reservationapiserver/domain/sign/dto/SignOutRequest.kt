package com.thepan.reservationapiserver.domain.sign.dto

import jakarta.validation.constraints.NotBlank

data class SignOutRequest(
    @field:NotBlank
    val accessToken: String
)
