package com.thepan.reservationapiserver.domain.member.entity

enum class RoleType(val type: String) {
    ROLE_ALLOW("허용유저"),
    ROLE_NOT_ALLOW("비허용휴저"),
    ROLE_STOP("정지"),
    ROLE_SLEEPER("휴면"),
    ROLE_ADMIN("관리자"),
    ROLE_MASTER("마스터")
}