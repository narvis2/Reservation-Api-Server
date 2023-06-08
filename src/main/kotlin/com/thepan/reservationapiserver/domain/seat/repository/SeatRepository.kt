package com.thepan.reservationapiserver.domain.seat.repository

import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long> {
    
    fun findBySeatTypeIn(seatTypes: List<SeatType>): List<Seat>
}