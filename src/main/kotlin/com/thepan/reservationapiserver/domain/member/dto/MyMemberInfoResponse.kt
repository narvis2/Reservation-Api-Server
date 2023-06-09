package com.thepan.reservationapiserver.domain.member.dto

import com.thepan.reservationapiserver.domain.member.entity.RoleType

data class MyMemberInfoResponse(
    val id: Long?,
    val email: String,
    val phoneNumber: String,
    val role: RoleType
)
