package com.thepan.reservationapiserver.config.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtTokenProperties(
    val issuer: String,
    val secretKey: String,
    val refreshSecretKey: String,
    val expireTime: Long,
    val refreshExpireTime: Long
)
