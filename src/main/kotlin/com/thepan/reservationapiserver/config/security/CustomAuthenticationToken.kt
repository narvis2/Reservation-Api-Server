package com.thepan.reservationapiserver.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    private val principal: CustomUserDetails,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {
    
    init {
        super.setAuthenticated(true)
    }
    
    override fun getCredentials(): Any {
        throw UnsupportedOperationException()
    }
    
    override fun getPrincipal(): CustomUserDetails = principal
}