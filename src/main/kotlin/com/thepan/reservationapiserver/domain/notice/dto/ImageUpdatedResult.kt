package com.thepan.reservationapiserver.domain.notice.dto

import com.thepan.reservationapiserver.domain.notice.entity.NoticeImage
import org.springframework.web.multipart.MultipartFile

data class ImageUpdatedResult(
    val addedImageFiles: List<MultipartFile>,
    val addedImages: List<NoticeImage>,
    val deletedImages: List<NoticeImage>
)
