package com.thepan.reservationapiserver.utils

/**
 * @author choi young-jun
 * 첫 번째 List 의 요소 중 하나라도 두 번째 List 에 포함되는지 여부를 체크하는 함수
 * 포함되면 true
 * 포함되지 않으면 false
 */
fun <T> isCheckDuplicatedList(firstList: List<T>, secondList: List<T>): Boolean =
    firstList.any { secondList.contains(it) }