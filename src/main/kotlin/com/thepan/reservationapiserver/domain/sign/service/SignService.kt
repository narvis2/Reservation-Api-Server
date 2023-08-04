package com.thepan.reservationapiserver.domain.sign.service

import com.thepan.reservationapiserver.config.jwt.JwtTokenService
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.sign.dto.*
import com.thepan.reservationapiserver.domain.sms.service.NaverSensV2Service
import com.thepan.reservationapiserver.exception.*
import com.thepan.reservationapiserver.utils.PHONE_AUTH_CODE_EXPIRATION
import com.thepan.reservationapiserver.utils.getPhoneAuthCodeKey
import com.thepan.reservationapiserver.utils.makeRandomMessage
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
    private val redisTemplate: RedisTemplate<String, Any>,
    private val naverSensV2Service: NaverSensV2Service
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
    fun signOut(accessToken: String) {
        // Token 유효성 검사
        if (!jwtTokenService.validateAccessToken(accessToken)) {
            throw AuthenticationEntryPointException()
        }
        
        // Token 에서 subject 추출
        val memberId = jwtTokenService.extractAccessTokenSubject(accessToken)
        
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
        val removeBearerToken = jwtTokenService.removeBearerPrefix(accessToken)
        redisTemplate.opsForValue().set(removeBearerToken, "logout", expiration, TimeUnit.MILLISECONDS)
    }
    
    fun sendMobileVerificationCode(request: PhoneAuthRequest) {
        val authCodeKey = getPhoneAuthCodeKey(
            request.name,
            request.phoneNumber
        )
        
        val beforeCode = redisTemplate.opsForValue()[authCodeKey] as? String
        
        // 인증 번호를 재요청 했을 경우 이전에 인증번호 발급받았던 인증번호는 Redis 에서 제거
        if (!beforeCode.isNullOrEmpty()) {
            redisTemplate.delete(authCodeKey)
        }
        
        val randomAuthenticationCode = makeRandomMessage()
        
        val response = naverSensV2Service.sendSMSMessage(request.phoneNumber, randomAuthenticationCode)
        
        if (response.statusCode == "202" && response.statusName == "success") {
            redisTemplate.opsForValue().set(
                authCodeKey,
                randomAuthenticationCode,
                PHONE_AUTH_CODE_EXPIRATION,
                TimeUnit.MILLISECONDS
            )
        } else {
            throw NaverSMSFailureException("문자 발송에 실패하였습니다.")
        }
    }
    
    fun checkMobileVerificationCode(request: CheckPhoneAuthRequest) {
        val getSavedAuthenticationCode = redisTemplate.opsForValue()[
            getPhoneAuthCodeKey(
                request.name,
                request.phoneNumber
            )
        ] as? String
        
        when {
            getSavedAuthenticationCode.isNullOrEmpty() -> {
                throw PhoneAuthCheckFailureException()
            }
            
            getSavedAuthenticationCode != request.authenticationCode -> {
                throw PhoneAuthCheckFailureException()
            }
        }
        
        redisTemplate.delete(getPhoneAuthCodeKey(request.name, request.phoneNumber))
    }
    
    private fun validatePassword(password: String, member: Member) {
        if (!bCryptPasswordEncoder.matches(password, member.password))
            throw LoginFailureException()
    }
    
    companion object {
        private val log = KotlinLogging.logger {}
    }
}