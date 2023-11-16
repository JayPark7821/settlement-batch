package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.order.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import java.time.ZonedDateTime

/**
 * OrderItemRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/16/23
 */
interface OrderItemRepository:JpaRepository<OrderItem, Long> {
    fun findByShippedCompleteAtBetween(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime): List<OrderItem>
}