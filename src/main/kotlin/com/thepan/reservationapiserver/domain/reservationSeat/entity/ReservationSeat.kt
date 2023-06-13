package com.thepan.reservationapiserver.domain.reservationSeat.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import jakarta.persistence.*

@Entity(name = "reservation_seat")
class ReservationSeat(
    @Id
    @GeneratedValue
    @Column(name = "reservation_seat_id")
    var id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    var reservation: Reservation? = null,
    @ManyToOne
    @JoinColumn(name = "seat_id")
    var seat: Seat? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var timeType: TimeType
) : BaseEntity()