package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.sign.dto.SignInRequest
import com.thepan.reservationapiserver.domain.sign.dto.SignInResponse
import com.thepan.reservationapiserver.domain.sign.dto.SignOutRequest
import com.thepan.reservationapiserver.domain.sign.service.SignService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import kotlin.math.sign

@RestController
@RequestMapping("/api/v1/sign")
class SignApiController(
    private val signService: SignService
) {
    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(
        @Valid
        @RequestBody
        request: SignInRequest
    ): ApiResponse<SignInResponse> = ApiResponse.success(signService.signIn(request))
    
    @PostMapping("/signOut")
    @ResponseStatus(HttpStatus.OK)
    fun signOut(
        @Valid
        @RequestBody
        request: SignOutRequest
    ): ApiResponse<Unit> {
        signService.signOut(request)
        return ApiResponse.success()
    }
}