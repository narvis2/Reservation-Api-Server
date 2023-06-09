package com.thepan.reservationapiserver.domain.member.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable // JPA Entity 안의 Column 을 하나의 객체로써 사용을 하고 싶다면 사용
data class MemberRoleId(
    private val member: Member,
    private val role: Role
) : Serializable