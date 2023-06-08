package com.thepan.reservationapiserver.domain.reservation.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId")
    var id: Long? = null,
    @Column(nullable = false, length = 5)
    var name: String,
    @Column(nullable = false, length = 11)
    var phoneNumber: String,
    @Column(nullable = false)
    var reservationDateTime: LocalDateTime,
    @Column(nullable = false)
    var reservationCount: Int,
    @OneToMany(mappedBy = "reservation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var seat: MutableSet<ReservationSeat> = mutableSetOf()
) : BaseEntity() {
    constructor(
        name: String,
        phoneNumber: String,
        reservationDateTime: LocalDateTime,
        reservationCount: Int,
        seats: List<Seat>
    ) : this(
        name = name,
        phoneNumber = phoneNumber,
        reservationDateTime = reservationDateTime,
        reservationCount = reservationCount
    ) {
        this.seat = seats.map { s -> ReservationSeat(this, s) }.toMutableSet()
    }
}