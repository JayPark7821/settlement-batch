package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.command.PgSalesAmountMaterial
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import java.math.BigDecimal
import java.time.LocalDate

/**
 * PositiveDailySettlementCollection
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/21/23
 */
class PositiveDailySettlementCollection(
    private val item: OrderItem
) {

    fun getSettlementDaily(): SettlementDaily {
        val orderItemSnapshot = item.orderItemSnapshot
        val count = item.orderCount ?: 1
        val countToBigDecimal = count.toBigDecimal()
        val seller = orderItemSnapshot.seller
        val taxCalculator = TaxCalculator(orderItemSnapshot)
        val taxAmount = taxCalculator.getTaxAmount().multiply(countToBigDecimal)
        val pgSalesAmountMaterial = PgSalesAmountMaterial(
            sellPrice = orderItemSnapshot.sellPrice,
            promotionAmount = orderItemSnapshot.promotionAmount,
            mileageUsageAmount = orderItemSnapshot.mileageUsageAmount,
        )
        val pgCalculator = PgSalesAmountCalculator(pgSalesAmountMaterial)
        val pgSalesAmount = pgCalculator.getPgSalesAmount().multiply(countToBigDecimal)

        val commissionAmountCalculator = CommissionAmountCalculator(orderItemSnapshot)
        val commissionAmount = commissionAmountCalculator.getCommissionAmount().multiply(countToBigDecimal)

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