package com.thepan.reservationapiserver.domain.sign.dto

data class SignInResponse(
    val token: String,
    val refreshToken: String
)