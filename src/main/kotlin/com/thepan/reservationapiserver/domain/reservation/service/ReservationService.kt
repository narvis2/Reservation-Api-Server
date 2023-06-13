package com.thepan.reservationapiserver.domain.reservation.service

import com.thepan.reservationapiserver.domain.mapper.toEntity
import com.thepan.reservationapiserver.domain.mapper.toReservationAllResponseList
import com.thepan.reservationapiserver.domain.mapper.toSeatTypeList
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationStatusCondition
import com.thepan.reservationapiserver.domain.reservation.repository.ReservationRepository
import com.thepan.reservationapiserver.domain.seat.repository.SeatRepository
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val seatRepository: SeatRepository
) {
    private val log = KotlinLogging.logger {}
    
    @Transactional
    fun create(request: ReservationCreateRequest) {
        val seatList = seatRepository.findBySeatTypeIn(request.seat.toSeatTypeList())
    
        if (seatList.isEmpty())
            throw SeatNotFoundException()
    
        log.info("🌸 예약 등록 START =========================")
        reservationRepository.save(request.toEntity(seatList))
        log.info("🌸 예약 등록 END =========================")
    }
    
    fun readAll(): List<ReservationAllResponse> = reservationRepository.findAll().toReservationAllResponseList()
    
    fun getReservationStatus(condition: ReservationStatusCondition): List<ReservationAllResponse> =
        reservationRepository.findByReservationDate(condition.dateTime.toLocalDate()).toReservationAllResponseList()
}