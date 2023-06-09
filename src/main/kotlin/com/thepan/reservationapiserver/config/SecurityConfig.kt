package com.thepan.reservationapiserver.config

import com.thepan.reservationapiserver.config.jwt.JwtTokenService
import com.thepan.reservationapiserver.config.security.CustomAccessDeniedHandler
import com.thepan.reservationapiserver.config.security.CustomAuthenticationEntryPoint
import com.thepan.reservationapiserver.config.security.CustomUserDetailsService
import com.thepan.reservationapiserver.config.security.JwtTokenAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val tokenService: JwtTokenService,
    private val userDetailsService: CustomUserDetailsService
) {
    
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring()
                .requestMatchers(AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(AntPathRequestMatcher("/exception/**"))
        }
    }
    
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth -> // 인증, 인가 설정
                auth.requestMatchers(
                    "/api/v1/sign/**",
                ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/reservation").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/reservation").hasRole("MASTER")
                    .requestMatchers(HttpMethod.GET, "/api/v1/reservation/status").hasRole("MASTER")
                    .anyRequest() // 위의 요청을 제외한 나머지 요청
                    .authenticated() // 별도의 인가는 필요하지 않지만 인증이 접근할 수 있음
            }
            .exceptionHandling {
                it.accessDeniedHandler(CustomAccessDeniedHandler())
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
            }
            .addFilterBefore(JwtTokenAuthenticationFilter(tokenService, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
    
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}