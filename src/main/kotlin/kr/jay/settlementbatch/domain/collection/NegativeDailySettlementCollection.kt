package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.command.PgSalesAmountMaterial
import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import java.math.BigDecimal
import java.time.LocalDate

/**
 * NegativeDailySettlementCollection
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/26/23
 */
class NegativeDailySettlementCollection(
    private val item: ClaimItem
) {

    fun getSettlementDaily(): SettlementDaily{
        val orderItem = item.orderItem
        val orderItemSnapshot = orderItem.orderItemSnapshot

        val count = item.claimCount ?: -1
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

        val claimShippedAmountCalculator = ClaimShippedAmountCalculator(item)
        val claimShippingFeeAmount = claimShippedAmountCalculator.getClaimShippedAmount()

        return SettlementDaily(
            settlementDate = LocalDate.now(),
            orderNo = orderItem.orderNo,
            orderCount = count,
            orderItemNo = orderItem.orderItemSnapshotNo,
            sellerNo = orderItemSnapshot.sellerNo,
            sellerBusinessNumber = seller.businessNo,
            sellerName = seller.sellerName,
            sellType = seller.sellType,
            taxType =  orderItemSnapshot.taxType,
            taxAmount = taxAmount,
            commissionAmount = commissionAmount,
            pgSalesAmount = pgSalesAmount,
            couponDiscountAmount = orderItemSnapshot.promotionAmount,
            mileageUsageAmount = orderItemSnapshot.mileageUsageAmount,
            shippingFeeAmount = orderItemSnapshot.defaultDeliveryAmount,
            claimReceiptNo = item.claimReceiptNo,
            claimShippingFeeAmount = claimShippingFeeAmount,
        )
    }
}