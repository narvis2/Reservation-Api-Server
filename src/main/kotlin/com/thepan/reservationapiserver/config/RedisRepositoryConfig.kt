package com.thepan.reservationapiserver.config

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableRedisRepositories
class RedisRepositoryConfig(
    private val redisProperties: RedisProperties,
) {
    
    /**
     * 📌 lettuce
     * - RedisProperties 로 application.yml 에 저장한 host, post를 가지고 와서 연결
     */
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val lettuceConnectionFactory = LettuceConnectionFactory(redisProperties.host, redisProperties.port)
//        lettuceConnectionFactory.setPassword("1324")

        return lettuceConnectionFactory
    }

    
    
    // setKeySerializer, setValueSerializer 설정으로 redis-cli를 통해 직접 데이터를 보는게 가능
    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> =
        RedisTemplate<String, Any>().apply {
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
            connectionFactory = redisConnectionFactory()
        }
    
    companion object {
        private val log = KotlinLogging.logger {}
    }
}