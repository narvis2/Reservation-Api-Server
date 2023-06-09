package com.thepan.reservationapiserver.config.security

import com.thepan.reservationapiserver.domain.mapper.toCustomUserDetails
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * @author choi young-jun
 *
 * 📌 Spring Security Context 에 인증된 사용자 정보를 저장
 * - 이렇게하면 Security 에서 인증된 유저의 정보를 가져올 수 있음
 */
@Component
@Transactional(readOnly = true)
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    private val log = KotlinLogging.logger {}
    
    override fun loadUserByUsername(username: String): CustomUserDetails {
        log.info("🦋 CustomUserDetailService > loadUserByUsername > username 👉 $username")
        
        return memberRepository.findWithRolesById(username.toLong())?.toCustomUserDetails()
            ?: throw UsernameNotFoundException("🦋$username Can Not Found 🦋")
    }
}