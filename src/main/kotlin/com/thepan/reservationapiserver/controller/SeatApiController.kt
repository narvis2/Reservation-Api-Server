package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.domain.seat.service.SeatService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class SeatApiController(
    private val seatService: SeatService
) {
    
    @GetMapping("/seats")
    @ResponseStatus(HttpStatus.OK)
    fun readAll(): ApiResponse<List<SeatType>> = ApiResponse.success(seatService.readAll())
}