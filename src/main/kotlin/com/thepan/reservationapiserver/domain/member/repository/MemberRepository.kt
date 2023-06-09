package com.thepan.reservationapiserver.domain.member.repository

import com.thepan.reservationapiserver.domain.member.entity.Member
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    
    fun existsByEmail(email: String): Boolean
    
    @EntityGraph("Member.roles")
    fun findWithRolesById(id: Long): Member?
}