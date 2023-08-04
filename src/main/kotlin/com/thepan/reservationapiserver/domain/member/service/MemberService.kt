package com.thepan.reservationapiserver.domain.member.service

import com.thepan.reservationapiserver.config.security.SecurityUtils
import com.thepan.reservationapiserver.domain.mapper.toMyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.dto.MemberUpdateFcmTokenRequest
import com.thepan.reservationapiserver.domain.member.dto.MyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.exception.MemberNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun getMyMemberInfo(): MyMemberInfoResponse =
        getMemberToSecurity().toMyMemberInfoResponse()
    
    @Transactional
    fun updateFcmToken(request: MemberUpdateFcmTokenRequest) {
        val member = getMemberToSecurity()
        
        member.fcmToken = request.fcmToken
        memberRepository.save(member)
    }
    
    /**
     * @author choi young-jun
     * - 로그인 후 Security 에 등록되어있는 memberId 를 바탕으로 Member 조회하기
     */
    private fun getMemberToSecurity(): Member = memberRepository.findById(
        SecurityUtils.getAuthorizedMemberId().toLong()
    ).orElseThrow {
        MemberNotFoundException()
    }
}