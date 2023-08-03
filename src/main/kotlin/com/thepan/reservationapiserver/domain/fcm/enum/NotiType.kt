package com.thepan.reservationapiserver.domain.fcm.enum

enum class NotiType(val type: String) {
    NOTI_A("NOTI_A"), // 일반 안내 NOTIFICATION
    NOTI_B("NOTI_N"), // 공지사항 NOTIFICATION
    NOTI_E("NOTI_E"), // 이벤트 관련 NOTIFICATION
    NOTI_R("NOTI_R"), // 예약 관련 NOTIFICATION
    NOTI_M("NOTI_M"), // 마스터 및 관리자에게 보내는 NOTIFICATION
}