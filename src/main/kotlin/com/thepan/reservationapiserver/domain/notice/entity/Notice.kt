package com.thepan.reservationapiserver.domain.notice.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.notice.dto.ImageUpdatedResult
import com.thepan.reservationapiserver.domain.notice.dto.NoticeUpdateRequest
import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile

@Entity
class Notice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId")
    var id: Long? = null,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var content: String,
    @ManyToOne
    @JoinColumn(name = "memberId")
    var member: Member,
    /**
     * 📌 다대일 양방향
     * PERSIST 👉 Notice Entity 가 저장되면서 Notice Image Entity 도 같이 저장됨
     * mappedBy = "notice" 👉 참조하는 Entity 에 있는 변수 명
     */
    @OneToMany(mappedBy = "notice", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var images: MutableList<NoticeImage> = ArrayList()
) : BaseEntity() {
    fun addImages(added: List<NoticeImage>) { // 5
        added.forEach { i ->
            this.images.add(i)
            i.initNotice(this)
        }
    }
    
    fun update(request: NoticeUpdateRequest): ImageUpdatedResult {
        this.title = request.title
        this.content = request.content
        
        val result = findImageUpdatedResult(request.addedImages, request.deletedImages)
        addImages(result.addedImages)
        deleteImage(result.deletedImages)
        
        return result
    }
    
    private fun deleteImage(deleted: List<NoticeImage>) {
        deleted.forEach { item ->
            this.images.remove(item)
        }
    }
    
    private fun findImageUpdatedResult(
        addedImageFiles: List<MultipartFile>,
        deletedImageIds: List<Long>
    ): ImageUpdatedResult {
        val addedImages = convertImageFilesToImages(addedImageFiles)
        val deletedImages = convertImageIdsToImages(deletedImageIds)
        return ImageUpdatedResult(addedImageFiles, addedImages, deletedImages)
    }
    
    private fun convertImageIdsToImages(imageIds: List<Long>): List<NoticeImage> =
        imageIds.mapNotNull { convertImageIdToImage(it) }
    
    private fun convertImageIdToImage(id: Long): NoticeImage? =
        this.images.find { it.id == id }
    
    private fun convertImageFilesToImages(imageFiles: List<MultipartFile>): List<NoticeImage> =
        imageFiles.mapNotNull { imageFile ->
            imageFile.originalFilename?.let {
                NoticeImage(uniqueName = "", originName = it)
            }
        }
}