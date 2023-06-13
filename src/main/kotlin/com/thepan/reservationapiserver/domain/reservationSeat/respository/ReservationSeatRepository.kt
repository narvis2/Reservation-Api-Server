package com.thepan.reservationapiserver.domain.reservationSeat.respository

import com.thepan.reservationapiserver.domain.reservationSeat.entity.ReservationSeat
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationSeatRepository : JpaRepository<ReservationSeat, Long> {

}