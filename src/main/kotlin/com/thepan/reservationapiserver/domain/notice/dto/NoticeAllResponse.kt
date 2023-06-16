package com.thepan.reservationapiserver.domain.notice.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.thepan.reservationapiserver.domain.member.dto.MyMemberInfoResponse
import java.time.LocalDateTime

data class NoticeAllResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: MyMemberInfoResponse,
    val images: List<NoticeImageResponse>,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val modifiedAt: LocalDateTime?,
)
