package com.thepan.reservationapiserver

import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.entity.RoleType
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.member.repository.RoleRepository
import com.thepan.reservationapiserver.exception.RoleNotFoundException
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Profile("local")
class InitDB(
    private val memberRepository: MemberRepository,
    private val roleRepository: RoleRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    private val log = KotlinLogging.logger {}
    
    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun initDb() {
        log.info("🌹 initDB Called 🌹")
        initAdminAndMasterUser()
    }
    
    private fun initAdminAndMasterUser() {
        val adminMasterList = ArrayList<Member>()
        
        val admin = Member(
            name = "관리자",
            email = "admin1@naver.com",
            phoneNumber = "01022696141",
            password = bCryptPasswordEncoder.encode("dudwns@651342"),
            roles = listOf(
                roleRepository.findByRoleType(RoleType.ROLE_ADMIN) ?: throw RoleNotFoundException()
            )
        )
        adminMasterList.add(admin)
        
        val master = Member(
            name = "마스터",
            email = "master1@naver.com",
            phoneNumber = "01022696141",
            password = bCryptPasswordEncoder.encode("dudwns@651342"),
            roles = listOf(
                roleRepository.findByRoleType(RoleType.ROLE_ADMIN) ?: throw RoleNotFoundException()
            )
        )
        adminMasterList.add(master)
        
        memberRepository.saveAll(adminMasterList)
    }
}