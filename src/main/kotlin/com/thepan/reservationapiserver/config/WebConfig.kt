package com.thepan.reservationapiserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration

@EnableWebMvc
@Configuration
class WebConfig(
    @Value("\${upload.image.location}")
    private val location: String
) : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/image/**")
            .addResourceLocations("file:$location") // 파일 시스템의 location 경로에서 파일에 접근
            .setCacheControl(CacheControl.maxAge(Duration.ofHours(1L)).cachePublic())
    }
}