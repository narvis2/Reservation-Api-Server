package com.thepan.reservationapiserver.domain.member.dto

import jakarta.validation.constraints.NotBlank

data class MemberUpdateFcmTokenRequest(
    @field: NotBlank(message = "Firebase Cloud Message Token 을 입력해주세요.")
    val fcmToken: String
)
