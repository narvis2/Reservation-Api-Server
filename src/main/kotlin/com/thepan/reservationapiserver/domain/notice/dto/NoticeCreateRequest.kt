package com.thepan.reservationapiserver.domain.notice.dto

import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile

data class NoticeCreateRequest(
    @field:NotBlank(message = "제목을 입력해주세요.")
    val title: String,
    @field:NotBlank(message = "내용을 입력해주세요.")
    val content: String,
    val images: List<MultipartFile> = ArrayList(),
)
