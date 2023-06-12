package com.thepan.reservationapiserver.config.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtils {
    fun getAuthorizedMemberId(): String {
        val principal: Any = SecurityContextHolder.getContext().authentication.principal
        
        return if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }
    }
}