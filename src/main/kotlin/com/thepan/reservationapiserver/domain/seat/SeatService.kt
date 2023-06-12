package com.thepan.reservationapiserver.domain.seat

import com.thepan.reservationapiserver.domain.mapper.toSeatResponseList
import com.thepan.reservationapiserver.domain.seat.dto.SeatResponse
import com.thepan.reservationapiserver.domain.seat.repository.SeatRepository
import org.springframework.stereotype.Service

@Service
class SeatService(
    private val seatRepository: SeatRepository
) {
    fun readAll(): List<SeatResponse> = seatRepository.findAll().toSeatResponseList()
}