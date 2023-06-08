package com.thepan.reservationapiserver.domain.reservation.repository

import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, Long> {
}