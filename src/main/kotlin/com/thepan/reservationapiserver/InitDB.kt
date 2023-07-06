package com.thepan.reservationapiserver

import com.thepan.reservationapiserver.domain.banner.entity.BannerImage
import com.thepan.reservationapiserver.domain.banner.repository.BannerImageRepository
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.entity.RoleType
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.member.repository.RoleRepository
import com.thepan.reservationapiserver.domain.notice.entity.Notice
import com.thepan.reservationapiserver.domain.notice.entity.NoticeImage
import com.thepan.reservationapiserver.domain.notice.respository.NoticeRepository
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
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val bannerImageRepository: BannerImageRepository,
    private val noticeRepository: NoticeRepository
) {
    private val log = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun initDb() {
        log.info("🌹 initDB Called 🌹")
        initAdminAndMasterUser()
        initBannerImage()
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
                roleRepository.findByRoleType(RoleType.ROLE_ADMIN) ?: throw RoleNotFoundException(),
                roleRepository.findByRoleType(RoleType.ROLE_MASTER) ?: throw RoleNotFoundException()
            )
        )
        adminMasterList.add(master)

        memberRepository.saveAll(adminMasterList)

        val noticeEntity = Notice(
            title = "우회담 입니다.",
            content = "잘 부탁드려요.",
            member = master,
        )

        val noticeImage = NoticeImage(
            uniqueName = "",
            originName = "IMG_9559.JPG",
        ).apply {
            initNotice(noticeEntity)
        }

        noticeEntity.addImages(listOf(noticeImage))

        noticeRepository.save(noticeEntity)
    }

    private fun initBannerImage() {
        val bannerImageList = ArrayList<BannerImage>()

        val bannerImage1 = BannerImage(uniqueName = "", originName = "IMG_9559.JPG")
        bannerImage1.uniqueName = "deadbbca-36cc-470a-a4f5-c0175c654a62.JPG"

        val bannerImage2 = BannerImage(uniqueName = "", originName = "IMG_9561.JPG")
        bannerImage2.uniqueName = "d34db6cc-9ebc-407e-b7aa-1ebef7b2d148.JPG"

        val bannerImage3 = BannerImage(uniqueName = "", originName = "IMG_9557.JPG")
        bannerImage3.uniqueName = "8e18e30e-519f-41b4-8b42-aba48b0c1def.JPG"

        bannerImageList.add(bannerImage1)
        bannerImageList.add(bannerImage2)
        bannerImageList.add(bannerImage3)

        bannerImageRepository.saveAll(bannerImageList)
    }
}