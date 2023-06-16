package com.thepan.reservationapiserver.domain.file

import com.thepan.reservationapiserver.exception.FileUploadFailureException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@Service
class LocalFileServiceImpl(
    @Value("\${upload.image.location}")
    private val location: String
) : FileService {
    // 파일을 업로드할 디렉토리를 생성
    @PostConstruct
    fun postConstruct() {
        val dir = File(location)
        if (!dir.exists()) {
            dir.mkdir()
        }
    }
    
    // MultipartFile 을 실제 파일로 지정된 위치에 저장
    override fun upload(file: MultipartFile, filename: String) {
        try {
            file.transferTo(File(location + filename))
        } catch (e: IOException) {
            throw FileUploadFailureException(e)
        }
    }
    
    override fun delete(filename: String) {
        File(location + filename).delete()
    }
}