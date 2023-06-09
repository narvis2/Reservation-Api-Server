package com.thepan.reservationapiserver.config.jwt

import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenHelper {
    
    /**
     * @author choi young-jun
     *
     * @param issuer : 발행자
     * @param subject : Token 에 저장될 데이터, 여기선 member_id
     * @param secretKey : 서명용 (이 key 를 바탕으로 AccessToken 인지, RefreshToken 인지 구분함)
     * @param expirationTime : 만료 시간 (AccessToken : 2시간, RefreshToken : 7일)
     * @return JWT Token
     */
    fun createToken(
        issuer: String,
        subject: String,
        secretKey: String,
        expirationTime: Long
    ): String {
        val now = Date()
        
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header Type : JWT
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime * 1000L))
            .setSubject(subject)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }
    
    // 📌 Token 에서 Subject 추출 즉, Member Id 추출
    fun extractSubject(secretKey: String, token: String): String = decodeToken(secretKey, token).body.subject
    
    // 📌 Token 유효성 검사
    fun validateToken(secretKey: String, token: String): Boolean =
        try {
            decodeToken(secretKey, token)
            true
        } catch (e: JwtException) {
            false
        }
    
    // 📌 Token Decoding
    private fun decodeToken(secretKey: String, token: String): Jws<Claims> = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(removeBearer(token))
    
    // 📌 접두사 `Bearer ` 제거
    private fun removeBearer(token: String): String = token.substring(TYPE.length)
    
    companion object {
        const val TYPE = "Bearer "
        const val HEADER_AUTHORIZATION = "Authorization"
    }
}