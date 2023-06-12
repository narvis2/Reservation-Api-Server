package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.domain.base.ApiResponse
import com.thepan.reservationapiserver.domain.member.dto.MyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberApiController(
    private val memberService: MemberService
) {
    
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    fun getMyMemberInfo(): ApiResponse<MyMemberInfoResponse> =
        ApiResponse.success(memberService.getMyMemberInfo())
}