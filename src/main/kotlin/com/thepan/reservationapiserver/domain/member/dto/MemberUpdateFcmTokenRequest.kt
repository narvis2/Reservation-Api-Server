package com.thepan.reservationapiserver.domain.member.dto

data class MemberUpdateFcmTokenRequest(
    val fcmToken: String? // 로그아웃하면 null 을 넣어줘야하기 때문에 null 허용
)
