package com.thepan.reservationapiserver.domain.sign.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class SignOutRequest(
    @field:NotNull
    @field:PositiveOrZero(message = "올바른 memberId를 입력해주세요.")
    val memberId: Long
)
