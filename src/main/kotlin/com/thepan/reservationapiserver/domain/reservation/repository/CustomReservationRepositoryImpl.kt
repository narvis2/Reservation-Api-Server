package com.thepan.reservationapiserver.domain.reservation.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections.constructor
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationRangeSectionDataResponse
import com.thepan.reservationapiserver.domain.reservation.dto.ReservationRangeSectionResponse
import com.thepan.reservationapiserver.domain.reservation.dto.enum.ReservationFilterType
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationConditionResponse
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationDateRangeRequest
import com.thepan.reservationapiserver.domain.reservation.dto.page.ReservationReadConditionRequest
import com.thepan.reservationapiserver.domain.reservation.entity.QReservation.reservation
import com.thepan.reservationapiserver.domain.reservation.entity.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author choi young-jun
 * - QueryDSL 을 사용한 PageNation 구현
 */
@Repository
@Transactional(readOnly = true) // 조회를 수행하므로 readOnly 로 설정
class CustomReservationRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Reservation::class.java), CustomReservationRepository {
    override fun findAllByCondition(condition: ReservationReadConditionRequest): Page<ReservationConditionResponse> {
        val pageable = PageRequest.of(condition.page, condition.size)
        
        val predicate = BooleanBuilder().and(orConditionsByEqFilter(condition.filterType))
        val results = fetchAll(predicate, pageable) ?: listOf()
        val totalCount = fetchCount(predicate) ?: 0
        
        return PageImpl(results, pageable, totalCount)
    }
    
    override fun findRangeGroupBy(request: ReservationDateRangeRequest): List<ReservationRangeSectionResponse> {
        return jpaQueryFactory
            .selectFrom(reservation)
            .where(reservation.reservationDateTime.between(request.searchStartDate, request.searchEndDate))
            .orderBy(reservation.reservationDateTime.asc())
            .transform(
                groupBy(reservation.reservationDateTime).list(
                    constructor(
                        ReservationRangeSectionResponse::class.java,
                        reservation.reservationDateTime,
                        reservation.timeType,
                        list(
                            constructor(
                                ReservationRangeSectionDataResponse::class.java,
                                reservation.id,
                                reservation.name,
                                reservation.phoneNumber,
                                reservation.reservationDateTime,
                                reservation.reservationCount,
                                reservation.isTermAllAgree,
                                reservation.isUserValidation,
                                reservation.certificationNumber,
                                reservation.timeType,
                            )
                        )
                    )
                )
            )
    }
    
    // 예약 목록을 ReservationAllResponse 로 조회한 결과르 반환
    private fun fetchAll(predicate: Predicate, pageable: Pageable): List<ReservationConditionResponse>? =
        querydsl?.applyPagination(
            pageable,
            jpaQueryFactory.select(constructor(
                ReservationConditionResponse::class.java,
                reservation.id,
                reservation.name,
                reservation.phoneNumber,
                reservation.reservationDateTime,
                reservation.reservationCount,
                reservation.timeType,
                reservation.certificationNumber,
                reservation.createdAt
            )).from(reservation)
                .join(reservation.seat)
                .where(predicate)
                .distinct()
                .orderBy(reservation.reservationDateTime.asc())
        )?.fetch()
    
    
    // 파라미터로 전달받은 조건식의 count Query 를 수행한 결과값 반환
    private fun fetchCount(predicate: Predicate): Long? =
        jpaQueryFactory.select(reservation.count()).from(reservation).where(predicate).fetchOne()
    
    private fun orConditionsByEqFilter(filterType: ReservationFilterType): BooleanExpression {
        return when (filterType) {
            ReservationFilterType.ALL -> reservation.id.isNotNull
            ReservationFilterType.NON_AUTH -> reservation.certificationNumber.isNull
            ReservationFilterType.AUTH -> reservation.certificationNumber.isNotNull
        }
    }
    
    private fun <T> orConditions(values: List<T>, term: (T) -> BooleanExpression): Predicate? =
        values.stream().map { term(it) }.reduce(BooleanExpression::or).orElse(null)
}