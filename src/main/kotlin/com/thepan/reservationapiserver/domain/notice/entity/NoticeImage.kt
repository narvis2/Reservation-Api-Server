package com.thepan.reservationapiserver.domain.notice.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.exception.UnsupportedImageFormatException
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*

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
    
    private fun generateUniqueName(extension: String): String = UUID.randomUUID().toString() + "." + extension
    
    // 이미지 이름에서 확장자를 추출
    private fun extractExtension(originName: String): String {
        try {
            val ext = originName.substring(originName.lastIndexOf(".") + 1)
            if (isSupportedFormat(ext))
                return ext
        } catch (e: StringIndexOutOfBoundsException) {
            throw UnsupportedImageFormatException()
        }
        
        throw UnsupportedImageFormatException()
    }
    
    // 지원하는 확장자인지 확인
    private fun isSupportedFormat(ext: String): Boolean = supportedExtension.any {
        it.equals(ext, ignoreCase = true)
    }
    
    companion object {
        // 해당 이미지가 지원하는 이미지 확장자
        private val supportedExtension = arrayOf("jpg", "jpeg", "gif", "bmp", "png")
    }
}