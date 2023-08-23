package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.reservation.ReservationService
import com.thepan.reservationapiserver.domain.reservation.dto.*
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationListResponse
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationReadConditionRequest
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ReservationApiController(
    private val reservationService: ReservationService
) {

    @PostMapping("/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid
        @RequestBody
        request: ReservationCreateRequest
    ): ApiResponse<Unit> {
        reservationService.create(request)

        return ApiResponse.success()
    }
    
    @GetMapping("/reservation/{id}")
    fun read(
        @PathVariable id: Long
    ): ApiResponse<ReservationDetailResponse> = ApiResponse.success(reservationService.read(id))
    
    @GetMapping("/reservation/user")
    fun readByUser(
        @Valid
        request: ReservationDetailByUserRequest
    ): ApiResponse<ReservationDetailResponse> = ApiResponse.success(reservationService.readByUser(request))

    @GetMapping("/reservation")
    @ResponseStatus(HttpStatus.OK)
    fun readAll(): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.readAll())

    @GetMapping("/reservation/non-auth")
    @ResponseStatus(HttpStatus.OK)
    fun readAllNonAuth(): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.readAllNonAuth())

    @GetMapping("/reservation/status")
    @ResponseStatus(HttpStatus.OK)
    fun readToCondition(
        @Valid
        condition: ReservationStatusCondition
    ): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.getReservationStatus(condition))
    
    @GetMapping("/reservation/seats/date")
    fun readReservedDateSeatList(
        @Valid
        condition: ReservationTargetDateRequest
    ): ApiResponse<List<ReservationTargetDateResponse>> =
        ApiResponse.success(reservationService.getTargetDateReservationSeatList(condition))

    @GetMapping("/reservation/seats")
    @ResponseStatus(HttpStatus.OK)
    fun readReservedSeatList(
        @Valid
        request: ReservationSeatListRequest
    ): ApiResponse<List<SeatType>> = ApiResponse.success(reservationService.getTargetReservationSeatList(request))

    @GetMapping("/reservation/count")
    @ResponseStatus(HttpStatus.OK)
    fun countReservedClients(
        @Valid
        request: ReservationClientCountRequest
    ): ApiResponse<List<ReservationClientCountResponseInterface>> =
        ApiResponse.success(reservationService.getReservationClientCount(request))

    @PutMapping("/reservation/check-auth/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateNonAuth(
        @PathVariable
        id: Long,
        @Valid
        @RequestBody
        request: ReservationApprovalCheckRequest
    ): ApiResponse<Unit> {
        reservationService.updateAuthorizedReservation(id, request)
        return ApiResponse.success()
    }

    @GetMapping("/reservation/non-auth/day-time")
    @ResponseStatus(HttpStatus.OK)
    fun readDayAndTimeTypeNonAuth(@Valid request: ReservationNotApporveRequest): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.getReservationDayAndTimeTypeNonAuth(request))
    
    @GetMapping("/reservation/filter")
    @ResponseStatus(HttpStatus.OK)
    fun readConditionFilter(
        @Valid
        condition: ReservationReadConditionRequest
    ): ApiResponse<ReservationListResponse> =
        ApiResponse.success(reservationService.readPageNationReservationList(condition))
}