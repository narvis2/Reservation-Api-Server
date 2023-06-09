package com.thepan.reservationapiserver.domain.notice.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.utils.extractExtension
import com.thepan.reservationapiserver.utils.generateUniqueName
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class NoticeImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var uniqueName: String,
    @Column(nullable = false)
    var originName: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var notice: Notice? = null
) : BaseEntity() {
    init {
        this.uniqueName = generateUniqueName(extractExtension(originName))
    }
    
    // Notice 의 연관 관계에 대한 정보가 없다면 이를 등록
    fun initNotice(notice: Notice) {
        if (this.notice == null) {
            this.notice = notice
        }
    }
}