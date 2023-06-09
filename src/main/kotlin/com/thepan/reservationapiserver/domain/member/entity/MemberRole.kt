package com.thepan.reservationapiserver.domain.member.entity

import jakarta.persistence.*

@Entity
@IdClass(MemberRoleId::class)
class MemberRole(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: Member,
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    val role: Role,
)