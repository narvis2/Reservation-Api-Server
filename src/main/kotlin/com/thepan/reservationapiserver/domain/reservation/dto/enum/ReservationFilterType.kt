package com.thepan.reservationapiserver.domain.reservation.dto.enum

enum class ReservationFilterType {
    ALL, // 전체 조회
    NON_AUTH, // 비인증된 예약만 조회 (마스터가 허용x)
    AUTH // 인증된 예약만 조회 (마스터가 허용o)
}