package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemSnapshotRepository : JpaRepository<OrderItemSnapshot, Long> {
}