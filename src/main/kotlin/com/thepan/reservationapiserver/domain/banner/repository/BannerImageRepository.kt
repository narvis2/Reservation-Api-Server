package com.thepan.reservationapiserver.domain.banner.repository

import com.thepan.reservationapiserver.domain.banner.entity.BannerImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BannerImageRepository : JpaRepository<BannerImage, Long> {
    @Query("SELECT b FROM BannerImage b ORDER BY b.modifiedAt DESC")
    fun findLatestModifiedBanner(): List<BannerImage>
}