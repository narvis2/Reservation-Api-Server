package com.thepan.reservationapiserver.config.jwt

import org.springframework.stereotype.Service

@Service
class JwtTokenService(private val jwtTokenHelper: JwtTokenHelper, private val jwtTokenProperties: JwtTokenProperties) {
    
    fun createAccessToken(subject: String): String = with(jwtTokenProperties) {
        jwtTokenHelper.createToken(issuer, subject, secretKey, expireTime)
    }
    
    fun createRefreshToken(subject: String): String = with(jwtTokenProperties) {
        jwtTokenHelper.createToken(issuer, subject, refreshSecretKey, refreshExpireTime)
    }
    
    fun validateAccessToken(token: String): Boolean = jwtTokenHelper.validateToken(jwtTokenProperties.secretKey, token)
    
    fun validateRefreshToken(refreshToken: String): Boolean = jwtTokenHelper.validateToken(jwtTokenProperties.refreshSecretKey, refreshToken)
    
    fun extractAccessTokenSubject(token: String): String = jwtTokenHelper.extractSubject(jwtTokenProperties.secretKey, token)
    
    fun extractRefreshTokenSubject(refreshToken: String): String = jwtTokenHelper.extractSubject(jwtTokenProperties.refreshSecretKey, refreshToken)
}