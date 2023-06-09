package com.thepan.reservationapiserver.domain.member.repository

import com.thepan.reservationapiserver.domain.member.entity.Role
import com.thepan.reservationapiserver.domain.member.entity.RoleType
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleType(roleType: RoleType): Role?
}