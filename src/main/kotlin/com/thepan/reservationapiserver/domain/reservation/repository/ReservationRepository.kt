package com.thepan.reservationapiserver.domain.reservation.repository

import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime

interface ReservationRepository : JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE CAST(r.reservationDateTime AS date) = :date")
    fun findByReservationDate(date: LocalDate): List<Reservation>
    
    // 📌 내 예약 정보를 가져옴
    @Query("SELECT r FROM Reservation r WHERE r.id = :reservationId AND r.timeType = :timeType AND r.reservationDateTime = :reservationDateTime")
    fun findByReservationIdAndTimeTypeAndDateTime(
        @Param("reservationId") reservationId: Long,
        @Param("timeType") timeType: TimeType,
        @Param("reservationDateTime") reservationDateTime: LocalDateTime
    ): Reservation?
    
    // 📌 예약 중복 체크에 사용됨
    @Query("SELECT r FROM Reservation r WHERE r.name = :name AND r.phoneNumber = :phoneNumber AND r.timeType = :timeType AND r.reservationDateTime = :reservationDateTime")
    fun findByAllCondition(
        @Param("name") name: String,
        @Param("phoneNumber") phoneNumber: String,
        @Param("timeType") timeType: TimeType,
        @Param("reservationDateTime") reservationDateTime: LocalDateTime
    ): Reservation?
    
    /**
     * 📌 지정된 날짜에 예약된 정보 List 가져오기
     * - 좌석 중복 체크에 사용될 것 임
     * TODO:: r.seat 로 바꾸기
     */
    @Query("SELECT r FROM Reservation r WHERE r.timeType = :timeType AND r.reservationDateTime = :reservationDateTime")
    fun findByTimeTypeAndDateTime(
        @Param("timeType") timeType: TimeType,
        @Param("reservationDateTime") reservationDateTime: LocalDateTime
    ): List<Reservation>
}