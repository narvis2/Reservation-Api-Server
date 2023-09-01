package com.thepan.reservationapiserver.domain.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.thepan.reservationapiserver.domain.fcm.dto.FCMNotificationRequest
import com.thepan.reservationapiserver.domain.member.repository.MemberRepository
import com.thepan.reservationapiserver.domain.reservation.repository.ReservationRepository
import com.thepan.reservationapiserver.exception.AuthenticationEntryPointException
import com.thepan.reservationapiserver.exception.MemberNotFoundException
import com.thepan.reservationapiserver.exception.ReservationNotFoundException
import org.springframework.stereotype.Service

@Service
class FCMNotificationService(
    private val firebaseMessaging: FirebaseMessaging,
    private val memberRepository: MemberRepository,
    private val reservationRepository: ReservationRepository
) {
    
    // 📌 예약자에게 알림 보내기
    fun sendNotificationReservation(request: FCMNotificationRequest) {
        if (request.targetId == null) throw ReservationNotFoundException()
        
        val targetReservation = reservationRepository.findById(request.targetId).orElseThrow {
            throw ReservationNotFoundException()
        }
        
        targetReservation.fcmToken?.let { token ->
            sendFCMPush(token, request.title, request.body, request.data)
        }
    }
    
    // 📌 마스터에게 알림 보내기
    fun sendNotificationMaster(request: FCMNotificationRequest) {
        if (request.targetId == null) throw MemberNotFoundException()
        
        val targetMaster = memberRepository.findById(request.targetId).orElseThrow {
            MemberNotFoundException()
        }
        
        targetMaster.fcmToken?.let { token ->
            if (targetMaster.isPushEnable) {
                sendFCMPush(token, request.title, request.body, request.data)
            }
        }
    }
    
    private fun sendFCMPush(fcmToken: String, title: String, body: String, data: Map<String, String>?) {
        val notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()
        
        val message = Message.builder()
            .setToken(fcmToken)
            .setNotification(notification)
            .putAllData(data)
            .build()
    
        firebaseMessaging.send(message)
    }
}