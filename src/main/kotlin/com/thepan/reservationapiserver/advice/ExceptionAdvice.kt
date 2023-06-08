package com.thepan.reservationapiserver.advice

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
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
    
    // 📌 Validation 진행중 오류 발생,
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    fun bindException(e: BindException): ApiResponse<Unit> {
        // 각 Validation 어노테이션 별로 지정해놨던 message 를 응답
        return ApiResponse.failure(-1001, e.bindingResult.fieldError?.defaultMessage)
    }
    
    @ExceptionHandler(SeatNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    fun seatNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1002, "요청한 좌석을 찾을 수 없습니다.")
    }
    
    // 📌 아예 잘못된 형식으로 request 를 요청한 경우 예외 발생
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponse<Unit> {
        return ApiResponse.failure(-1003, e.message)
    }
}