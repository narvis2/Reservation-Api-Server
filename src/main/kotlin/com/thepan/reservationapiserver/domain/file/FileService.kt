package com.thepan.reservationapiserver.domain.file

import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun upload(file: MultipartFile, filename: String)
    fun delete(filename: String)
}