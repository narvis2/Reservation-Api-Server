package com.thepan.reservationapiserver.domain.mapper

import com.thepan.reservationapiserver.config.security.CustomUserDetails
import com.thepan.reservationapiserver.domain.member.dto.MyMemberInfoResponse
import com.thepan.reservationapiserver.domain.member.entity.Member
import com.thepan.reservationapiserver.domain.member.entity.MemberRole
import com.thepan.reservationapiserver.domain.member.entity.RoleType
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationAllResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationCreateRequest
import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import com.thepan.reservationapiserver.domain.seat.entity.Seat
import com.thepan.reservationapiserver.domain.seat.entity.SeatType
import com.thepan.reservationapiserver.exception.SeatNotFoundException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

fun ReservationCreateRequest.toEntity(seats: List<Seat>): Reservation = Reservation(
    name = this.name,
    phoneNumber = this.phoneNumber,
    reservationDateTime = this.reservationDateTime,
    reservationCount = this.reservationCount,
    seats = seats
)

fun List<Reservation>.toReservationAllResponseList(): List<ReservationAllResponse> = map {
    ReservationAllResponse(
        id = it.id,
        name = it.name,
        phoneNumber = it.phoneNumber,
        reservationDateTime = it.reservationDateTime,
        reservationCount = it.reservationCount,
        seat = it.seat.map { s -> s.seat }
    )
}

fun List<String>.toSeatTypeList(): List<SeatType> {
    val seatTypes = SeatType.values()
    
    // all 👉 모든 조건이 맞아야 true 를 반환
    return if (all { item ->
            seatTypes.any { it.name == item }
        }) {
        mapNotNull { seatTypeString ->
            seatTypes.firstOrNull { it.name == seatTypeString }
        }
    } else {
        throw SeatNotFoundException()
    }
}

fun Member.toCustomUserDetails() = CustomUserDetails(
    userId = id.toString(),
    password = password,
    authorities = roles.toAuthorities()
)

private fun MutableSet<MemberRole>.toAuthorities(): Set<GrantedAuthority> = stream().map {
    it.role
}.map {
    it.roleType
}.map {
    print("🦋 CustomUserDetails roleType 👉 $it \n")
    it.toString()
}.map(::SimpleGrantedAuthority).collect(Collectors.toSet())

fun Member.toMyMemberInfoResponse(): MyMemberInfoResponse = MyMemberInfoResponse(
    id = id,
    email = email,
    phoneNumber = phoneNumber,
    role = roles.toRoleTypeList().toRoleType()
)

private fun MutableSet<MemberRole>.toRoleTypeList(): List<RoleType> = map {
    it.role
}.map {
    it.roleType
}

private fun List<RoleType>.toRoleType(): RoleType =
    when {
        contains(RoleType.ROLE_MASTER) -> RoleType.ROLE_MASTER
        contains(RoleType.ROLE_ADMIN) -> RoleType.ROLE_ADMIN
        contains(RoleType.ROLE_NOT_ALLOW) -> RoleType.ROLE_NOT_ALLOW
        contains(RoleType.ROLE_ALLOW) -> RoleType.ROLE_ALLOW
        contains(RoleType.ROLE_SLEEPER) -> RoleType.ROLE_SLEEPER
        contains(RoleType.ROLE_STOP) -> RoleType.ROLE_STOP
        
        else -> RoleType.ROLE_STOP
    }