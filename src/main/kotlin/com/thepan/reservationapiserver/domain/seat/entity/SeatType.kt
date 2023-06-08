package com.thepan.reservationapiserver.domain.seat.entity

/**
 * @author choi young-jun
 * A 👉 1인석, 좌석 8개
 * B 👉 4인석
 * C 👉 6인석
 */
enum class SeatType(val type: String) {
    A_1("A-1"),
    A_2("A-2"),
    A_3("A-3"),
    A_4("A-4"),
    A_5("A-5"),
    A_6("A-6"),
    A_7("A-7"),
    A_8("A-8"),
    B("B"),
    C("C")
}
