package com.thepan.reservationapiserver.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class CustomUserDetails(
    val userId: String,
    private val password: String,
    private val authorities: Set<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.stream().collect(Collectors.toSet())
    }
    
    // 📌 사용자의 비밀번호 반환(비밀번호는 암호화해서 저장해야함)
    override fun getPassword(): String = password
    
    // 📌 사용자 id 를 반환(unique)
    override fun getUsername(): String = userId
    
    /**
     * 📌 계정이 만료 여부 반환
     * - true : 만료 X
     * - false : 만료 O
     */
    override fun isAccountNonExpired(): Boolean = true
    
    /**
     * 📌 계정이 잠금되어있는지 여부 반환
     * - true : 잠금 X
     * - false : 만료 O
     */
    override fun isAccountNonLocked(): Boolean = true
    
    /**
     * 📌 패스워드 만료 여부 반환
     * - true : 만료 X,
     * - false : 만료 O
     */
    override fun isCredentialsNonExpired(): Boolean = true
    
    /**
     * 📌 계정 사용 가능 여부 반환
     * - true : 사용 가능,
     * - false : 사용 불가
     */
    override fun isEnabled(): Boolean = true
}