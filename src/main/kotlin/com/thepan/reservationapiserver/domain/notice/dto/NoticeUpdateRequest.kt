package com.thepan.reservationapiserver.domain.notice.dto

import org.springframework.web.multipart.MultipartFile

data class NoticeUpdateRequest(
    val title: String,
    val content: String,
    val addedImages: List<MultipartFile> = listOf(), // 추가된 Image List
    val deletedImages: List<Long> = listOf() // 제거된 Image id List
)
