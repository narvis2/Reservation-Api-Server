package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.sign.dto.*
import com.thepan.reservationapiserver.domain.sign.service.SignService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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
        @RequestHeader(value = "Authorization") authorizationHeader: String
    ): ApiResponse<Unit> {
        signService.signOut(authorizationHeader)
        return ApiResponse.success()
    }
    
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    fun refreshToken(
        @RequestHeader(value = "Authorization") refreshToken: String
    ): ApiResponse<RefreshTokenResponse> = ApiResponse.success(signService.refreshToken(refreshToken))
    
    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    fun signPhone(
        @Valid
        @RequestBody
        request: PhoneAuthRequest
    ): ApiResponse<Unit> {
        signService.sendMobileVerificationCode(request)
        return ApiResponse.success()
    }
    
    @PostMapping("/phone/check")
    @ResponseStatus(HttpStatus.OK)
    fun checkPhone(
        @Valid
        @RequestBody
        request: CheckPhoneAuthRequest
    ): ApiResponse<Unit> {
        signService.checkMobileVerificationCode(request)
        return ApiResponse.success()
    }
}