package com.thepan.reservationapiserver.domain.reservation.repository

import com.thepan.reservationapiserver.domain.reservation.dto.ReservationClientCountResponseInterface
import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import com.thepan.reservationapiserver.domain.reservation.entity.ReservationSeat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

interface ReservationRepository : JpaRepository<Reservation, Long>, CustomReservationRepository {
    @Query("select r from Reservation r where r.certificationNumber is null")
    fun findNonAuth(): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE CAST(r.reservationDateTime AS date) = :date")
    fun findByReservationDate(date: LocalDate): List<Reservation>
    
    @Query("SELECT r FROM Reservation r WHERE r.certificationNumber = :certificationNumber")
    fun findByCertificationNumber(
        @Param("certificationNumber") certificationNumber: String
    ): Reservation?

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
        @Param("reservationDateTime") reservationDateTime: Date
    ): Reservation?

    /**
     * 📌 지정된 날짜에 예약된 정보 List 가져오기
     * - 좌석 중복 체크에 사용될 것 임
     */
    @Query("SELECT r.seat FROM Reservation r WHERE r.timeType = :timeType AND r.reservationDateTime = :reservationDateTime")
    fun findByTimeTypeAndDateTime(
        @Param("timeType") timeType: TimeType,
        @Param("reservationDateTime") reservationDateTime: Date
    ): MutableSet<ReservationSeat>

    /**
     * 📌 해당 이름과, 전화번호로 예약이 몇번 됐는지 Count List 가져오기
     * - 받은 파람값이 있으면 해당 손님의 예약 횟수를, 없다면 전체 손님의 예약 횟수를 가져옴
     */
    @Query("SELECT r.name AS name , r.phoneNumber AS phoneNumber, count(r.name) AS reservationCount FROM Reservation r WHERE r.name LIKE %:name% AND r.phoneNumber LIKE %:phoneNumber% GROUP BY r.name")
    fun findByUserNameAndPhoneNumber(
        @Param("name") name: String?,
        @Param("phoneNumber") phoneNumber: String?
    ): List<ReservationClientCountResponseInterface>

    /**
     * 📌 해당 날짜, 해당 시간에 예약 승인 안되어있는 예약 List 가져오기
     */
    fun findByTimeTypeAndReservationDateTimeAndCertificationNumberIsNull(
        timeType: TimeType,
        reservationDateTime: Date
    ): List<Reservation>
}