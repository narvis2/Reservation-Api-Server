package com.thepan.reservationapiserver.domain.reservation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.*
import java.time.LocalDateTime

data class ReservationCreateRequest(
    @field:NotBlank(message = "성함을 입력해주세요.")
    val name: String,
    @field:NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @field:Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$", message = "유효하지 않은 휴대폰 번호입니다.")
    val phoneNumber: String,
    @field:NotNull(message = "예약 날짜를 입력해주세요.")
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    @field:Future(message = "예약시간은 현재시간보다 미래여야 합니다.")
    val reservationDateTime: LocalDateTime,
    @field:NotNull(message = "예약인원 수를 입력해주세요.")
    @field:Positive(message = "올바른 예약인원 수를 입력해주세요. (0이상)")
    val reservationCount: Int,
    @field:NotBlank(message = "예약 파트 타임을 입력해주세요.")
    val timeType: String,
    @field:AssertTrue(message = "이용약관에 모두 동의해주세요.")
    val isTermAllAgree: Boolean,
    @field:AssertTrue(message = "본인 인증을 해주세요.")
    val isUserValidation: Boolean,
    @field:NotNull(message = "예약 좌석을 입력해주세요.")
    @field:Size(min = 1, max = 10)
    val seat: List<String>,
    val fcmToken: String?
)
