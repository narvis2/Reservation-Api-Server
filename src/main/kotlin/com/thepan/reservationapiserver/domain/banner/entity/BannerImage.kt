package com.thepan.reservationapiserver.domain.banner.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.utils.extractExtension
import com.thepan.reservationapiserver.utils.generateUniqueName
import jakarta.persistence.*

@Entity
class BannerImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var uniqueName: String,
    @Column(nullable = false)
    var originName: String,
) : BaseEntity() {
    init {
        this.uniqueName = generateUniqueName(extractExtension(originName))
    }
}