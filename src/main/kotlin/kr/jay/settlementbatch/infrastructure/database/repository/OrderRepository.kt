package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.order.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
}