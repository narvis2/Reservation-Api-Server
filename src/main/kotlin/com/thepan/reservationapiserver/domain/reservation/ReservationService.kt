package com.thepan.reservationapiserver.domain.reservation

import com.thepan.reservationapiserver.domain.mapper.toEntity
import com.thepan.reservationapiserver.domain.mapper.toReservationAllResponseList
import com.thepan.reservationapiserver.domain.mapper.toSeatTypeList
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationStatusCondition
import com.thepan.reservationapiserver.domain.reservation.repository.ReservationRepository
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import com.thepan.reservationapiserver.domain.seat.repository.SeatRepository
import com.thepan.reservationapiserver.exception.DuplicateConferenceException
import com.thepan.reservationapiserver.exception.DuplicateConferenceSeatException
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import com.thepan.reservationapiserver.utils.isCheckDuplicatedList
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
        checkIsDuplicateConference(request)
        val seatList = checkIsValidSeatName(request)
        checkIsDuplicateSeat(request)
    
        log.info("🌸 예약 등록 START =========================")
        reservationRepository.save(request.toEntity(seatList))
        log.info("🌸 예약 등록 END =========================")
    }
    
    fun readAll(): List<ReservationAllResponse> = reservationRepository.findAll().toReservationAllResponseList()
    
    fun getReservationStatus(condition: ReservationStatusCondition): List<ReservationAllResponse> =
        reservationRepository.findByReservationDate(condition.dateTime.toLocalDate()).toReservationAllResponseList()
    
    // 📌 중복 예약 체크
    private fun checkIsDuplicateConference(request: ReservationCreateRequest) {
        val isDuplicateReservation = reservationRepository.findByAllCondition(
            name = request.name,
            phoneNumber = request.phoneNumber,
            timeType = stringToTimeType(request.timeType),
            reservationDateTime = request.reservationDateTime
        ) !== null
        
        if (isDuplicateReservation)
            throw DuplicateConferenceException()
    }
    
    // 📌 Seat 명이 올바른지 체크
    private fun checkIsValidSeatName(request: ReservationCreateRequest): List<Seat> {
        val seatList = seatRepository.findBySeatTypeIn(request.seat.toSeatTypeList())
        
        if (seatList.isEmpty())
            throw SeatNotFoundException()
        
        return seatList
    }
    
    private fun checkIsDuplicateSeat(request: ReservationCreateRequest) {
        val selectedSeatList = checkIsValidSeatName(request)
        
        val allSeatList = seatRepository.findAll()
        
        if (allSeatList.isEmpty())
            throw SeatNotFoundException()
        
        val reservationStoredInTime = reservationRepository.findByTimeTypeAndDateTime(
            stringToTimeType(request.timeType),
            request.reservationDateTime
        )
        
        if (reservationStoredInTime.isEmpty()) return
        
        val reservedSeatList = reservationStoredInTime.flatMap {
            it.seat
        }.map {
            it.seat
        }
        
        val checkSeat = isCheckDuplicatedList<Seat>(selectedSeatList, reservedSeatList)
        
        if (checkSeat)
            throw DuplicateConferenceSeatException()
    }
    
    private fun stringToTimeType(timeType: String): TimeType = TimeType.valueOf(timeType)
}