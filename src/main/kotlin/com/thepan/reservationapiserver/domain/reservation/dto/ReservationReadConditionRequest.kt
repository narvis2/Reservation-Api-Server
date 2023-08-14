package com.thepan.reservationapiserver.domain.reservation.dto

import com.thepan.reservationapiserver.domain.reservation.dto.enum.ReservationFilterType
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class ReservationReadConditionRequest(
    
    @field:NotNull(message = "페이지 번호를 입력해주세요.")
    @field:PositiveOrZero(message = "올바른 페이지 번호를 입력해주세요. (0 이상)") // 해당 값이 0 또는 양의 정수인지 검사. 즉, 0보다 크거나 같은지
    val page: Int,
    
    @field:NotNull(message = "페이지 크기를 입력해주세요.")
    @field:Positive(message = "올바른 페이지 크기를 입력해주세요. (1 이상)") // 해당 값이 정수인지 의미, 즉 양수이면서 0보다 큰지 검사
    val size: Int,
    
    @field:NotNull(message = "필터 타입을 입력해주세요.")
    val filterType: ReservationFilterType
)
