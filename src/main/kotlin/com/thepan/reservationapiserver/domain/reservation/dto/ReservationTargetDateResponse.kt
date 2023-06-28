package com.thepan.reservationapiserver.domain.reservation.dto

import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.domain.seat.entity.TimeType

data class ReservationTargetDateResponse(
    val partTime: TimeType,
    val remainsSeatList: List<SeatType>
)
