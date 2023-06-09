package com.thepan.reservationapiserver.controller

import com.thepan.reservationapiserver.exception.AccessDeniedException
import com.thepan.reservationapiserver.exception.AuthenticationEntryPointException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExceptionApiController {
    @GetMapping("/exception/entry-point")
    fun entryPoint() {
        throw AuthenticationEntryPointException()
    }
    
    @GetMapping("/exception/access-denied")
    fun accessDenied() {
        throw AccessDeniedException()
    }
}