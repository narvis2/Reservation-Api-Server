package com.thepan.reservationapiserver.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

/**
 * @author choi young-jun
 *
 * 📌 인증되지 않은 사용자의 접근이 거부되었을 때 작동할 핸들러 (JWT Token 이 없는 경우)
 * - Controller 계층에 도달하기 전에 수행되기 때문에 ExceptionAdvice 에서 이 예외를 잡아낼 수 없음
 * -  예외 사항을 다루는 방식의 일관성을 위해 /exception/entry-point 로 요청을 보내서
 *    Controller 안에서 throw 를 던져 ExceptionAdvice 에서 처리될 수 있도록 했음
 */
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    // 인증이 필요한 자원에 대한 요청이 들어올 때, 해당 요청이 인증되지 않은 상태인 경우에 호출
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response?.sendRedirect("/exception/entry-point")
    }
}