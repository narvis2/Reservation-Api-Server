package com.thepan.reservationapiserver.domain.seat.service

import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.domain.seat.repository.SeatRepository
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import org.springframework.stereotype.Service

@Service
class SeatService(
    private val seatRepository: SeatRepository
) {
    
    fun readAll(): List<SeatType> {
        val seatAllList = seatRepository.findAll()
        if (seatAllList.isEmpty()) throw SeatNotFoundException()
        
        return seatAllList.map { it.seatType }
    }
}