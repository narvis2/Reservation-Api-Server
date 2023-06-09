package com.thepan.reservationapiserver.domain.member.service

import com.thepan.reservationapiserver.config.security.SecurityUtils
import com.thepan.reservationapiserver.domain.mapper.toMyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.dto.MyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.exception.MemberNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun getMyMemberInfo(): MyMemberInfoResponse =
        memberRepository.findById(
            SecurityUtils.getAuthorizedMemberId().toLong()
        ).orElseThrow {
            MemberNotFoundException()
        }.toMyMemberInfoResponse()
}