package com.thepan.reservationapiserver.domain.notice.service

import com.thepan.reservationapiserver.config.security.SecurityUtils
import com.thepan.reservationapiserver.domain.file.FileService
import com.thepan.reservationapiserver.domain.mapper.toEntity
import com.thepan.reservationapiserver.domain.mapper.toNoticeAllResponseList
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.notice.dto.NoticeAllResponse
import com.thepan.reservationapiserver.domain.notice.dto.NoticeCreateRequest
import com.thepan.reservationapiserver.domain.notice.dto.NoticeUpdateRequest
import com.thepan.reservationapiserver.domain.notice.entity.Notice
import com.thepan.reservationapiserver.domain.notice.entity.NoticeImage
import com.thepan.reservationapiserver.domain.notice.respository.NoticeRepository
import com.thepan.reservationapiserver.exception.MemberNotFoundException
import com.thepan.reservationapiserver.exception.NoticeNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository,
    private val memberRepository: MemberRepository,
    private val fileService: FileService
) {
    
    @Transactional
    fun create(request: NoticeCreateRequest) {
        val writerMemberId = SecurityUtils.getAuthorizedMemberId()
        val writer = memberRepository.findById(writerMemberId.toLong()).orElseThrow {
            MemberNotFoundException()
        }
        
        val notice = noticeRepository.save(request.toEntity(writer))
    
        uploadImage(notice.images, request.images)
    }
    
    fun readAll(): List<NoticeAllResponse> =
        noticeRepository.findAll().toNoticeAllResponseList()
    
    @Transactional
    fun delete(id: Long) {
        val notice = getNoticeFindById(id)
        deleteImage(notice.images)
        noticeRepository.delete(notice)
    }
    
    @Transactional
    fun update(id: Long, request: NoticeUpdateRequest) {
        val notice = getNoticeFindById(id)
        val result = notice.update(request)
    
        uploadImage(result.addedImages, result.addedImageFiles)
        deleteImage(result.deletedImages)
    }
    
    private fun getNoticeFindById(id: Long): Notice = noticeRepository.findById(id).orElseThrow { NoticeNotFoundException() }
    
    private fun uploadImage(
        images: List<NoticeImage>,
        fileImages: List<MultipartFile>
    ) {
        images.indices.forEachIndexed { index, _ ->
            fileService.upload(fileImages[index], images[index].uniqueName)
        }
    }
    
    private fun deleteImage(images: List<NoticeImage>) {
        images.forEach { item ->
            fileService.delete(item.uniqueName)
        }
    }
}