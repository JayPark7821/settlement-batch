package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.collection.TaxCalculator
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import org.springframework.batch.item.ItemProcessor
import java.math.BigDecimal
import java.time.LocalDate

/**
 * DailySettlementItemProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
class DailySettlementItemProcessor: ItemProcessor<OrderItem, SettlementDaily> {
    override fun process(item: OrderItem): SettlementDaily? {
        val orderItemSnapshot = item.orderItemSnapshot
        val count = item.orderCount
        val seller = orderItemSnapshot.seller
        val taxCalculator = TaxCalculator(orderItemSnapshot)
        val taxAmount = taxCalculator.getTaxAmount()

        val pgSalesAmount = BigDecimal.ZERO

        val commissionAmount = BigDecimal.ZERO

        return SettlementDaily(
            settlementDate = LocalDate.now(),
            orderNo = item.orderNo,
            orderCount = count,
            orderItemNo = item.orderItemSnapshotNo,
            sellerNo = orderItemSnapshot.sellerNo,
            sellerBusinessNumber = seller.businessNo,
            sellerName = seller.sellerName,
            sellType = seller.sellType,
            taxType =  orderItemSnapshot.taxType,
            taxAmount = taxAmount,
            commissionAmount = commissionAmount,
            pgSalesAmount = pgSalesAmount,
        )

    }
}