package com.thepan.reservationapiserver.advice

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.exception.*
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MultipartException

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
    
    @ExceptionHandler(RoleNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun roleNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1004, "요청한 등급을 찾을 수 없습니다.")
    }
    
    @ExceptionHandler(LoginFailureException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    fun loginFailureException(e: LoginFailureException): ApiResponse<Unit> {
        return ApiResponse.failure(-1005, "로그인에 실패하였습니다.")
    }
    
    // 📌 인증되지 않은 사용자, 401 응답
    @ExceptionHandler(AuthenticationEntryPointException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authenticationEntryPoint(): ApiResponse<Unit> {
        return ApiResponse.failure(-1006, "인증되지 않은 사용자입니다.")
    }
    
    // ✅ 인증은 되었으나 권한이 없는 경우, 403 응답
    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun accessDeniedException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1007, "접근 권한이 없습니다.")
    }
    
    @ExceptionHandler(MemberNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun memberNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1008, "요청하신 회원을 찾을 수 없습니다.")
    }
    
    @ExceptionHandler(DuplicateConferenceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun duplicateConferenceException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1009, "같은 타임에 중복된 예약입니다.")
    }
    
    @ExceptionHandler(DuplicateConferenceSeatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun duplicateConferenceSeatException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1010, "중복된 좌석입니다.")
    }
    
    @ExceptionHandler(NetworkErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun networkErrorException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1011, "네트워크가 원활하지 않습니다. 잠시후 다시 시도해주시기 바랍니다.")
    }
    
    @ExceptionHandler(NaverSMSFailureException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun naverSMSFailureException(e: NaverSMSFailureException): ApiResponse<Unit> {
        return ApiResponse.failure(-1012, e.message)
    }
    
    @ExceptionHandler(PhoneAuthCheckFailureException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun phoneAuthCheckFailureException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1013, "휴대폰 인증에 실패하였습니다.")
    }
    
    @ExceptionHandler(UnsupportedImageFormatException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun unsupportedImageFormatException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1014, "지원하는 확장자가 아닙니다.")
    }
    
    @ExceptionHandler(FileUploadFailureException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun fileUploadFailureException(e: FileUploadFailureException): ApiResponse<Unit> {
        log.error("파일 업로드 실패 에러 👉 ${e.message}")
        return ApiResponse.failure(-1015, "파일 업로드에 실패하였습니다.");
    }
    
    @ExceptionHandler(NoticeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun noticeNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1016, "요청하신 공지사항을 찾을 수 없습니다.");
    }
    
    @ExceptionHandler(ReservationNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun reservationNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1017, "요청하신 예약 정보를 찾을 수 없습니다.");
    }
    
    @ExceptionHandler(BannerImageNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bannerImageNotFoundException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1018, "요청하신 배너 이미지를 찾을 수 없습니다.")
    }
    
    // 📌 Multipart Upload 시 용량초과 Exception
    @ExceptionHandler(MultipartException::class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    fun multipartException(): ApiResponse<Unit> {
        return ApiResponse.failure(-1019, "파일 용량초과, 요청하신 파일이 너무 큽니다.")
    }
}