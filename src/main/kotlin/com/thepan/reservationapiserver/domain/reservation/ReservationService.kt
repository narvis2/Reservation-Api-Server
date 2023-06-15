package com.thepan.reservationapiserver.domain.reservation

import com.thepan.reservationapiserver.domain.mapper.toEntity
import com.thepan.reservationapiserver.domain.mapper.toReservationAllResponseList
import com.thepan.reservationapiserver.domain.mapper.toSeatTypeList
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationSeatListRequest
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationStatusCondition
import com.thepan.reservationapiserver.domain.reservation.repository.ReservationRepository
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import com.thepan.reservationapiserver.domain.seat.repository.SeatRepository
import com.thepan.reservationapiserver.exception.DuplicateConferenceException
import com.thepan.reservationapiserver.exception.DuplicateConferenceSeatException
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import com.thepan.reservationapiserver.utils.isCheckDuplicatedList
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

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
    
    // 📌 특정 날짜에 남아있는 좌석 List 가져오기
    fun getTargetReservationSeatList(request: ReservationSeatListRequest): List<SeatType> {
        val reservedSeatList = getReservationInfoList(request.timeType, request.reservationDateTime)
        val allSeatList = seatRepository.findAll()
    
        return if (reservedSeatList.isEmpty()) {
            allSeatList.map { it.seatType }
        } else {
            // 같은 날짜에 이미 예약되어있는 좌석 제거하고 남아있는 좌석만 가져오기
            val leftReservationList = allSeatList.filterNot { it in reservedSeatList }
        
            leftReservationList.map { it.seatType }
        }
    }
    
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
    
    /**
     * 📌 중복된 좌석 체크
     * - 해당 날짜로 조회해서 예약 정보가 없으면 👉 좌석이 모두 있다는 의미, 더 이상 밑에 로직 탈 필요없이 return 처리
     * - 중복된 좌석이 있으면 DuplicateConferenceSeatException 발생
     */
    private fun checkIsDuplicateSeat(request: ReservationCreateRequest) {
        val selectedSeatList = checkIsValidSeatName(request)
    
        val allSeatList = seatRepository.findAll()
    
        if (allSeatList.isEmpty())
            throw SeatNotFoundException()
    
        val reservedSeatList = getReservationInfoList(
            request.timeType,
            request.reservationDateTime
        )
    
        if (reservedSeatList.isEmpty()) return
    
        val checkSeat = isCheckDuplicatedList(selectedSeatList, reservedSeatList)
    
        if (checkSeat)
            throw DuplicateConferenceSeatException()
    }
    
    private fun getReservationInfoList(
        timeType: String,
        reservationDateTime: LocalDateTime
    ): List<Seat> = reservationRepository.findByTimeTypeAndDateTime(
        stringToTimeType(timeType),
        reservationDateTime
    ).map { it.seat }
    
    private fun stringToTimeType(timeType: String): TimeType = TimeType.valueOf(timeType)
}