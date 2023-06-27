package com.thepan.reservationapiserver.utils

import com.thepan.reservationapiserver.exception.UnsupportedImageFormatException
import java.util.*

/**
 * @author choi young-jun
 * 첫 번째 List 의 요소 중 하나라도 두 번째 List 에 포함되는지 여부를 체크하는 함수
 * 포함되면 true
 * 포함되지 않으면 false
 */
fun <T> isCheckDuplicatedList(firstList: List<T>, secondList: List<T>): Boolean =
    firstList.any { secondList.contains(it) }

/**
 * @author choi young-jun
 * name, phoneNumber 를 바탕으로 Redis 에서
 * 핸드폰 인증번호를 가져오는 Key 를 만들어서 반환
 */
fun getPhoneAuthCodeKey(name: String, phoneNumber: String): String = "PA:${name}:${phoneNumber}"

/**
 * @author choi young-jun
 * - 0 ~ 9 사이의 숫자를 사용하여 5자리 랜덤한 숫자를 만들어 반환
 */
fun makeRandomMessage(): String {
    val rand = Random()
    var numStr = ""
    
    for (i in 0 until 6) {
        val ran = rand.nextInt(10).toString()
        numStr += ran
    }
    
    return numStr
}

/**
 * @author choi young-jun
 * - 알파벳과 숫자(0~9)의 조합으로 16 자리 String 을 만들어 반환
 * - 예약 인증 번호로 사용
 */
fun makeReservationRandomCode(): String {
    val characters = ('a'..'z') + ('0'..'9')
    val rand = Random()
    var text = ""
    
    for (i in 0 until 16) {
        val randomChar = characters[rand.nextInt(characters.size)]
        text += randomChar
    }
    
    return text
}

fun generateUniqueName(extension: String): String = UUID.randomUUID().toString() + "." + extension

// 이미지 이름에서 확장자를 추출
fun extractExtension(originName: String): String {
    try {
        val ext = originName.substring(originName.lastIndexOf(".") + 1)
        if (isSupportedFormat(ext))
            return ext
    } catch (e: StringIndexOutOfBoundsException) {
        throw UnsupportedImageFormatException()
    }
    
    throw UnsupportedImageFormatException()
}

// 지원하는 확장자인지 확인
private fun isSupportedFormat(ext: String): Boolean = supportedExtension.any {
    it.equals(ext, ignoreCase = true)
}

// 해당 이미지가 지원하는 이미지 확장자
private val supportedExtension = arrayOf("jpg", "jpeg", "gif", "bmp", "png")