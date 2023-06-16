package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.notice.dto.NoticeAllResponse
import com.thepan.reservationapiserver.domain.notice.dto.NoticeCreateRequest
import com.thepan.reservationapiserver.domain.notice.dto.NoticeUpdateRequest
import com.thepan.reservationapiserver.domain.notice.service.NoticeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class NoticeApiController(
    private val noticeService: NoticeService
) {
    
    /**
     * @ModelAttribute
     * - 게시글 데이터를 이미지와 함께 전달받을 수 있도록,
     *   요청하는 Content-Type이 multipart/form-data를 이용
     *
     * @ModelAttribute 에 대해 validation 제약 조건이 위배되면, BindException 예외가 발생
     */
    @PostMapping("/notice")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid
        @ModelAttribute
        request: NoticeCreateRequest
    ): ApiResponse<Unit> {
        noticeService.create(request)
        return ApiResponse.success()
    }
    
    @GetMapping("/notices")
    @ResponseStatus(HttpStatus.OK)
    fun readAll(): ApiResponse<List<NoticeAllResponse>> = ApiResponse.success(noticeService.readAll())
    
    @DeleteMapping("/notice/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(
        @PathVariable id: Long
    ): ApiResponse<Unit> {
        noticeService.delete(id)
        return ApiResponse.success()
    }
    
    @PutMapping("/notice/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable
        id: Long,
        @Valid
        @ModelAttribute
        request: NoticeUpdateRequest
    ): ApiResponse<Unit> {
        noticeService.update(id, request)
        return ApiResponse.success()
    }
}