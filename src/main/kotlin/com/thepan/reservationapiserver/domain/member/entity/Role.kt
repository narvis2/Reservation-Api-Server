package com.thepan.reservationapiserver.domain.member.entity

import jakarta.persistence.*

@Entity(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    var id: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    var roleType: RoleType,
)