package com.thepan.reservationapiserver.domain.seat.dto

import com.thepan.reservationapiserver.domain.seat.entity.SeatType

data class SeatResponse(
    val seat: SeatType
)
