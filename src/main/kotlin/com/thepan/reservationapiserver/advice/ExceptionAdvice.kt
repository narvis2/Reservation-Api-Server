package com.thepan.reservationapiserver.advice

import com.thepan.reservationapiserver.domain.base.ApiResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    
    private val log = KotlinLogging.logger {}
    
    /**
     * 📌 의도하지 않은 예외가 발생하면, 로그를 남겨주고 응답
     * @ExceptionHandler 에서 잡아내지 못한 예외가 여기로 옴
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(e: Exception): ApiResponse<Unit> {
        log.error("의도치 않은 API Error 👉", e)
        return ApiResponse.failure(-1000, "오류가 발생하였습니다.")
    }
    
    
}