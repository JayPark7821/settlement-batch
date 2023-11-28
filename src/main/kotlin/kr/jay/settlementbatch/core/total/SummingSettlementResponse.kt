package kr.jay.settlementbatch.core.total

import java.math.BigDecimal

/**
 * SummingSettlementResponse
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/27/23
 */
data class SummingSettlementResponse(
    val sellerNo: Long,
    val sellerName: String,
    val sellerBusinessNumber: String?,
    val taxType: String,
    val sellType: String,
    val sumPgSalesAmount: BigDecimal,
    val sumCouponDiscountAmount: BigDecimal,
    val sumMileageUsageAmount: BigDecimal,
    val sumShippingFeeAmount: BigDecimal,
    val sumClaimShippingFeeAmount: BigDecimal,
    val sumCommissionAmount: BigDecimal,
    val sumTasAmount: BigDecimal,
)
