package com.thepan.reservationapiserver.domain.reservationSeat.dto

import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.domain.seat.entity.TimeType

data class ReservationSeatResponse(
    val id: Long?,
    val reservation: ReservationAllResponse?,
    val seat: SeatType?,
    val timeType: TimeType
)
