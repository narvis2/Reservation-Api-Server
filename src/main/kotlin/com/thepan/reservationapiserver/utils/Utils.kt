package com.thepan.reservationapiserver.utils

import java.util.Random

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