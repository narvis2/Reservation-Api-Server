package com.thepan.reservationapiserver.domain.reservation.dto.page

data class ReservationListResponse(
    val totalCount: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val reservationList: List<ReservationConditionResponse>
)
