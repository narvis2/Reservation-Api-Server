package com.thepan.reservationapiserver.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

/**
 * @author choi young-jun
 *
 * 📌 인증된 사용자가 "권한 부족" 등의 사유로 인해 접근이 거부되었을 때 작동할 핸들러
 * - Controller 계층에 도달하기 전에 수행되기 때문에 ExceptionAdvice 에서 이 예외를 잡아낼 수 없음
 * -  예외 사항을 다루는 방식의 일관성을 위해 /exception/access-denied 로 요청을 보내서
 *    Controller 안에서 throw 를 던져 ExceptionAdvice 에서 처리될 수 있도록 했음
 */
class CustomAccessDeniedHandler : AccessDeniedHandler {
    // 인증된 사용자가 자원에 접근할 권한이 없을 때 호출
    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
        response?.sendRedirect("/exception/access-denied")
    }
}