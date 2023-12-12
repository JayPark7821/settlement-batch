package kr.jay.settlementbatch.application

import kr.jay.settlementbatch.domain.entity.Product
import kr.jay.settlementbatch.domain.entity.Seller
import kr.jay.settlementbatch.domain.entity.order.Order
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemSnapshotRepository
import kr.jay.settlementbatch.infrastructure.database.repository.OrderRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.ZonedDateTime

/**
 * DummyFacade
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/12/23
 */

@Service
class DummyFacade (
    private val orderItemRepository: OrderItemRepository,
    private val orderItemSnapshotRepository: OrderItemSnapshotRepository,
    private val orderRepository: OrderRepository
){
    fun createOrder() {
        val order = Order(
            paidPgAmount = BigDecimal.valueOf(10000.000)
        )

        orderRepository.save(order)

        val orderItemSnapshot = OrderItemSnapshot(
            productNo = 1,
            sellerNo = 1,
            sellPrice = BigDecimal.valueOf(10000.000),
            supplyPrice = BigDecimal.valueOf(1000.000),
            promotionAmount = BigDecimal.valueOf(2000.000),
            mileageUsageAmount = BigDecimal.valueOf(2000.000),
            seller = Seller(1, sellerName = "가나다", sellType = "P"),
            product = Product(1, productName = "테스트", sellerNo = 1, category = 1)
        )
        orderItemSnapshotRepository.save(orderItemSnapshot)

        val orderItem = OrderItem(
            orderNo = order.id!!,
            orderCount = 1,
            orderItemSnapshotNo = orderItemSnapshot.id!!,
            itemDeliveryStatus = 0,
            shippedCompleteAt = ZonedDateTime.now(),
            orderItemSnapshot = orderItemSnapshot
        )

        orderItemRepository.save(orderItem)


    }
}