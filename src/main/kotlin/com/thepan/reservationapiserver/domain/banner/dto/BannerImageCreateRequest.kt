package com.thepan.reservationapiserver.domain.banner.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

data class BannerImageCreateRequest(
    @field:NotNull(message = "배너 이미지는 Null 일 수 없습니다.")
    @field:Size(min = 1, message = "배너 이미지는 최소하나 이상으로 하셔야합니다.")
    val images: List<MultipartFile>
)
