package com.thepan.reservationapiserver.config

import com.querydsl.jpa.JPQLTemplates
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig {
    @PersistenceContext
    lateinit var entityManager: EntityManager
    
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager)
}