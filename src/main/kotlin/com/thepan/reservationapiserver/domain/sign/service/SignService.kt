package com.thepan.reservationapiserver.domain.sign.service

import com.thepan.reservationapiserver.config.jwt.JwtTokenService
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.sign.dto.SignInRequest
import com.thepan.reservationapiserver.domain.sign.dto.SignInResponse
import com.thepan.reservationapiserver.exception.LoginFailureException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenService: JwtTokenService
) {
    
    @Transactional(readOnly = true)
    fun signIn(request: SignInRequest): SignInResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw LoginFailureException()
        validatePassword(request.password, member)
        
        val token = jwtTokenService.createAccessToken(member.id.toString())
        val refreshToken = jwtTokenService.createRefreshToken(member.id.toString())
        return SignInResponse(token, refreshToken)
    }
    
    private fun validatePassword(password: String, member: Member) {
        if (!bCryptPasswordEncoder.matches(password, member.password))
            throw LoginFailureException()
    }
}