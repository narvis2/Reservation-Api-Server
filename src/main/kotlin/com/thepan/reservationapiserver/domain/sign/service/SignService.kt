package com.thepan.reservationapiserver.domain.sign.service

import com.thepan.reservationapiserver.config.jwt.JwtTokenHelper
import com.thepan.reservationapiserver.config.jwt.JwtTokenService
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.sign.dto.SignInRequest
import com.thepan.reservationapiserver.domain.sign.dto.SignInResponse
import com.thepan.reservationapiserver.domain.sign.dto.SignOutRequest
import com.thepan.reservationapiserver.exception.AuthenticationEntryPointException
import com.thepan.reservationapiserver.exception.LoginFailureException
import com.thepan.reservationapiserver.exception.MemberNotFoundException
import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
class SignService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenService: JwtTokenService,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    
    @Transactional(readOnly = true)
    fun signIn(request: SignInRequest): SignInResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw LoginFailureException()
        validatePassword(request.password, member)
    
        val token = jwtTokenService.createAccessToken(member.id.toString())
        val refreshToken = jwtTokenService.createRefreshToken(member.id.toString())
        val refreshTokenExpiresTime = jwtTokenService.getRefreshTokenExpiresTime()
    
        // Redis 에 Refresh Token 저장
        redisTemplate.opsForValue().set("RT:${member.email}", refreshToken, refreshTokenExpiresTime, TimeUnit.MILLISECONDS)
    
        return SignInResponse(token, refreshToken)
    }
    
    @Transactional(readOnly = true)
    fun signOut(request: SignOutRequest) {
        // Token 유효성 검사
        if (!jwtTokenService.validateAccessToken(JwtTokenHelper.TYPE + request.accessToken)) {
            throw AuthenticationEntryPointException()
        }
        
        // Token 에서 subject 추출
        val memberId = jwtTokenService.extractAccessTokenSubject(JwtTokenHelper.TYPE + request.accessToken)
        
        // 추출한 subject 를 바탕으로 Member 조회
        val member = memberRepository.findById(
            memberId.toLong()
        ).orElseThrow {
            MemberNotFoundException()
        }
        
        // Redis 에서 해당 Member Email 로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제
        if (redisTemplate.opsForValue()["RT:${member.email}"] != null) {
            redisTemplate.delete("RT:${member.email}")
        }
        
        // 해당 Access Token 유효시간을 가지고 와서 BlackList 에 저장
        val expiration = jwtTokenService.getAccessTokenExpiresTime()
        redisTemplate.opsForValue().set(request.accessToken, "logout", expiration, TimeUnit.MILLISECONDS)
    }
    
    private fun validatePassword(password: String, member: Member) {
        if (!bCryptPasswordEncoder.matches(password, member.password))
            throw LoginFailureException()
    }
    
    companion object {
        private val log = KotlinLogging.logger {}
    }
}