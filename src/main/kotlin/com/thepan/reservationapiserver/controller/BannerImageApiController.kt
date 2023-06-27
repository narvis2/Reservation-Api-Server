package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.banner.dto.BannerImageAllResponse
import com.thepan.reservationapiserver.domain.banner.dto.BannerImageCreateRequest
import com.thepan.reservationapiserver.domain.banner.service.BannerImageService
import com.thepan.reservationapiserver.domain.base.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class BannerImageApiController(
    private val bannerImageService: BannerImageService
) {
    
    @PostMapping("/banner/images")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(
        @Valid
        @ModelAttribute
        request: BannerImageCreateRequest
    ): ApiResponse<Unit> {
        bannerImageService.create(request)
        return ApiResponse.success()
    }
    
    @GetMapping("/banner/images")
    @ResponseStatus(HttpStatus.OK)
    fun readAll(): ApiResponse<List<BannerImageAllResponse>> =
        ApiResponse.success(bannerImageService.readAll())
    
    @DeleteMapping("banner/images/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(
        @PathVariable
        id: Long
    ): ApiResponse<Unit> {
        bannerImageService.delete(id)
        return ApiResponse.success()
    }
}