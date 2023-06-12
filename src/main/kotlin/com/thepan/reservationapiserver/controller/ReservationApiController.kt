package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationStatusCondition
import com.thepan.reservationapiserver.domain.reservation.service.ReservationService
import com.thepan.reservationapiserver.domain.reservationSeat.dto.ReservationSeatResponse
import com.thepan.reservationapiserver.domain.reservationSeat.service.ReservationSeatService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ReservationApiController(
    private val reservationService: ReservationService,
    private val reservationSeatService: ReservationSeatService
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
    
    @GetMapping("/reservation")
    @ResponseStatus(HttpStatus.OK)
    fun readAll(): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.readAll())
    
    @GetMapping("/reservation/status")
    @ResponseStatus(HttpStatus.OK)
    fun readToCondition(
        @Valid
        condition: ReservationStatusCondition
    ): ApiResponse<List<ReservationAllResponse>> =
        ApiResponse.success(reservationService.getReservationStatus(condition))
    
    @GetMapping("/reservation/seat")
    @ResponseStatus(HttpStatus.OK)
    fun readReservationSeatAll(): ApiResponse<List<ReservationSeatResponse>> =
        ApiResponse.success(reservationSeatService.readAll())
}