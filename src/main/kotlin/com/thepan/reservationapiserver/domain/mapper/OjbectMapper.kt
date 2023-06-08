package com.thepan.reservationapiserver.domain.mapper

import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.exception.SeatNotFoundException

fun ReservationCreateRequest.toEntity(seats: List<Seat>): Reservation = Reservation(
    name = this.name,
    phoneNumber = this.phoneNumber,
    reservationDateTime = this.reservationDateTime,
    reservationCount = this.reservationCount,
    seats = seats
)

fun List<Reservation>.toReservationAllResponseList(): List<ReservationAllResponse> = map {
    ReservationAllResponse(
        id = it.id,
        name = it.name,
        phoneNumber = it.phoneNumber,
        reservationDateTime = it.reservationDateTime,
        reservationCount = it.reservationCount,
        seat = it.seat.map { s -> s.seat }
    )
}

fun List<String>.toSeatTypeList(): List<SeatType> {
    val seatTypes = SeatType.values()
    
    // all 👉 모든 조건이 맞아야 true 를 반환
    return if (all { item ->
            seatTypes.any { it.name == item }
        }) {
        mapNotNull { seatTypeString ->
            seatTypes.firstOrNull { it.name == seatTypeString }
        }
    } else {
        throw SeatNotFoundException()
    }
}