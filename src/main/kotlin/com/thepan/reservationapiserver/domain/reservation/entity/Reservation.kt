package com.thepan.reservationapiserver.domain.reservation.entity

import com.thepan.reservationapiserver.domain.base.BaseEntity
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.TimeType
import jakarta.persistence.*
import org.springframework.data.jpa.repository.Temporal
import java.util.Date

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationId")
    var id: Long? = null,
    @Column(nullable = false, length = 5)
    var name: String,
    @Column(nullable = false, length = 11)
    var phoneNumber: String,
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var reservationDateTime: Date,
    @Column(nullable = false)
    var reservationCount: Int,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var timeType: TimeType,
    @Column(nullable = false)
    var isTermAllAgree: Boolean,
    @Column(nullable = false)
    var isUserValidation: Boolean,
    @Column(nullable = true)
    var certificationNumber: String? = null,
    @Column(nullable = true)
    var fcmToken: String?,
    /**
     * Reservation 에서 ReservationSeat 를 관리하기 위해 설정
     * cascade = [CascadeType.ALL] 👉 부모 Entity 에 대한 변경이 자식 Entity 에 영향을 미치도록
     * orphanRemoval = true 👉 부모와 연관이 끊어진 자식 Entity 를 자동으로 제거하도록 지정
     */
    @OneToMany(mappedBy = "reservation", cascade = [CascadeType.ALL], orphanRemoval = true)
    var seat: MutableSet<ReservationSeat> = mutableSetOf()
) : BaseEntity() {
    constructor(
        name: String,
        phoneNumber: String,
        reservationDateTime: Date,
        reservationCount: Int,
        timeType: TimeType,
        isTermAllAgree: Boolean,
        isUserValidation: Boolean,
        fcmToken: String?,
        seats: List<Seat>
    ) : this(
        name = name,
        phoneNumber = phoneNumber,
        reservationDateTime = reservationDateTime,
        timeType = timeType,
        reservationCount = reservationCount,
        isTermAllAgree = isTermAllAgree,
        fcmToken = fcmToken,
        isUserValidation = isUserValidation
    ) {
        this.seat = seats.map { s -> ReservationSeat(this, s) }.toMutableSet()
    }
}