package com.thepan.reservationapiserver.domain.reservation.entity

import com.thepan.reservationapiserver.domain.seat.entity.Seat
import jakarta.persistence.*

@Entity
@IdClass(ReservationSeatId::class)
class ReservationSeat(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId")
    val reservation: Reservation,
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId")
    val seat: Seat
)