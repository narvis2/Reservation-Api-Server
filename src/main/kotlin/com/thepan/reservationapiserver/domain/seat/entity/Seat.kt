package com.thepan.reservationapiserver.domain.seat.entity

import jakarta.persistence.*

@Entity(name = "seat")
class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId")
    var id: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    var seatType: SeatType
)