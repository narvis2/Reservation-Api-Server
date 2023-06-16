package com.thepan.reservationapiserver.domain.reservation.dto

interface ReservationClientCountResponseInterface {
    fun getName(): String
    fun getPhoneNumber(): String
    fun getReservationCount(): Int
}