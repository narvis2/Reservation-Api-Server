package com.thepan.reservationapiserver.domain.reservationSeat.service

import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservationSeat.dto.ReservationSeatResponse
import com.thepan.reservationapiserver.domain.reservationSeat.respository.ReservationSeatRepository
import org.springframework.stereotype.Service

@Service
class ReservationSeatService(
    private val reservationSeatRepository: ReservationSeatRepository
) {
    
    fun readAll(): List<ReservationSeatResponse> {
        val allEntity = reservationSeatRepository.findAll()
        val dto = allEntity.map {
            ReservationSeatResponse(
                id = it.id,
                reservation = it.reservation?.let { reservation ->
                    ReservationAllResponse(
                        id = reservation.id,
                        name = reservation.name,
                        phoneNumber = reservation.phoneNumber,
                        reservationDateTime = reservation.reservationDateTime,
                        reservationCount = reservation.reservationCount,
                        seat = reservation.seat.map { s -> s.seat }
                    )
                },
                seat = it.seat?.seatType,
                timeType = it.timeType
            )
        }
        
        return dto
    }
}