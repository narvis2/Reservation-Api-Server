package com.thepan.reservationapiserver.domain.member.service

import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

}