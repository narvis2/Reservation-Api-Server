package com.thepan.reservationapiserver.domain.banner.service

import com.thepan.reservationapiserver.domain.banner.dto.BannerImageAllResponse
import com.thepan.reservationapiserver.domain.banner.dto.BannerImageCreateRequest
import com.thepan.reservationapiserver.domain.banner.entity.BannerImage
import com.thepan.reservationapiserver.domain.banner.repository.BannerImageRepository
import com.thepan.reservationapiserver.domain.file.FileService
import com.thepan.reservationapiserver.domain.mapper.toBannerImageAllResponse
import com.thepan.reservationapiserver.exception.BannerImageNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class BannerImageService(
    private val bannerImageRepository: BannerImageRepository,
    private val fileService: FileService
) {
    @Transactional
    fun create(request: BannerImageCreateRequest) {
        val bannerImageList = request.images.map {
            BannerImage(uniqueName = "", originName = it.originalFilename!!)
        }
        
        val savedBannerImageList = bannerImageRepository.saveAll(bannerImageList)
        uploadImage(savedBannerImageList, request.images)
    }
    
    fun readAll(): List<BannerImageAllResponse> = bannerImageRepository.findLatestModifiedBanner().toBannerImageAllResponse()
    
    @Transactional
    fun delete(id: Long) {
        val bannerImage = getBannerImageFindById(id)
        
        deleteImage(bannerImage)
        bannerImageRepository.delete(bannerImage)
    }
    
    private fun getBannerImageFindById(id: Long): BannerImage = bannerImageRepository.findById(id).orElseThrow {
        BannerImageNotFoundException()
    }
    
    private fun uploadImage(
        images: List<BannerImage>,
        fileImages: List<MultipartFile>
    ) {
        images.indices.forEachIndexed { index, _ ->
            fileService.upload(fileImages[index], images[index].uniqueName)
        }
    }
    
    private fun deleteImage(image: BannerImage) {
        fileService.delete(image.uniqueName)
    }
}