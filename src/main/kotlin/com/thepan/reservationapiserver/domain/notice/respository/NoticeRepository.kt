package com.thepan.reservationapiserver.domain.notice.respository

import com.thepan.reservationapiserver.domain.notice.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, Long> {
    
    fun findByMemberId(memberId: Long): List<Notice>
}