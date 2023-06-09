package com.thepan.reservationapiserver.domain.reservation.repository

import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ReservationRepository : JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE CAST(r.reservationDateTime AS date) = :date")
    fun findByReservationDate(date: LocalDate): List<Reservation>
}