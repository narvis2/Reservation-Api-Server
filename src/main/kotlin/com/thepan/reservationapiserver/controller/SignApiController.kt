package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.config.jwt.JwtTokenHelper.Companion.HEADER_AUTHORIZATION
import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.sign.dto.*
import com.thepan.reservationapiserver.domain.sign.service.SignService
import jakarta.servlet.http.HttpServletRequest
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
        httpServletRequest: HttpServletRequest
    ): ApiResponse<Unit> {
        val accessToken = httpServletRequest.getHeader(HEADER_AUTHORIZATION)
        signService.signOut(accessToken)
        return ApiResponse.success()
    }
    
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