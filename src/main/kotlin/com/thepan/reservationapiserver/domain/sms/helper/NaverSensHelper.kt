package com.thepan.reservationapiserver.domain.sms.helper

import org.springframework.stereotype.Component
import java.nio.charset.UnsupportedCharsetException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class NaverSensHelper {
    
    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun makeSensSignature(
        url: String,
        timestamp: String,
        method: String,
        accessKey: String,
        secretKey: String
    ): String {
        val space = " "
        val newLine = "\n"
        
        val message = StringBuilder().apply {
            append(method)
            append(space)
            append(url)
            append(newLine)
            append(timestamp)
            append(newLine)
            append(accessKey)
        }.toString()
        
        return try {
            val signingKey = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "HmacSHA256")
            val mac: Mac = Mac.getInstance("HmacSHA256")
            mac.init(signingKey)
            
            val rawHmac = mac.doFinal(message.toByteArray(Charsets.UTF_8))
            Base64.getEncoder().encodeToString(rawHmac)
        } catch (e: UnsupportedCharsetException) {
            e.toString()
        }
    }
}