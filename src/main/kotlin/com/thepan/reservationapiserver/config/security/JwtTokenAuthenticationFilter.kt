package com.thepan.reservationapiserver.config.security

import com.thepan.reservationapiserver.config.jwt.JwtTokenHelper.Companion.HEADER_AUTHORIZATION
import com.thepan.reservationapiserver.config.jwt.JwtTokenHelper.Companion.TYPE
import com.thepan.reservationapiserver.config.jwt.JwtTokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * @author choi young-jun
 *
 * 📌 OncePerRequestFilter
 * - request 당 한 번만 실행되도록 보장 (동일한 request 에 대한 중복 실행을 방지)
 * - 인증이나 권한 부여에 관한 작업 수행
 */
class JwtTokenAuthenticationFilter(
    private val tokenService: JwtTokenService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    private val log = KotlinLogging.logger {}
    
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getHeader(HEADER_AUTHORIZATION).takeIf {
            it.isNotBlank() && it.startsWith(TYPE)
        }
        
        token?.let {
            if (tokenService.validateAccessToken(it)) {
                log.info("🌸🌸🌸 인증된 Token 👉 $it")
                setAccessAuthentication(token)
            }
        }
        
        filterChain.doFilter(request, response)
    }
    
    private fun setAccessAuthentication(token: String) {
        val memberId = tokenService.extractAccessTokenSubject(token)
        val userDetails: CustomUserDetails = userDetailsService.loadUserByUsername(memberId)
        SecurityContextHolder.getContext().authentication = CustomAuthenticationToken(userDetails, userDetails.authorities)
    }
}