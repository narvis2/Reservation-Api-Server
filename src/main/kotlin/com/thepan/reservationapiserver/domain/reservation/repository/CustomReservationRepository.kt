package com.thepan.reservationapiserver.domain.reservation.repository

import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationConditionResponse
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationReadConditionRequest
import org.springframework.data.domain.Page

/**
 * @author choi young-jun
 * - QueryDSL 을 이용하여 Query 를 작성하기 위해, Custom Repository 를 만듬
 */
interface CustomReservationRepository {
    fun findAllByCondition(condition: ReservationReadConditionRequest): Page<ReservationConditionResponse>
}