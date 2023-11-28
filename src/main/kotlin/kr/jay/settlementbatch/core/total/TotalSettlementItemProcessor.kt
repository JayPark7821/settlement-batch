package kr.jay.settlementbatch.core.total

import kr.jay.settlementbatch.domain.entity.settlement.SettlementTotal
import org.springframework.batch.item.ItemProcessor
import java.time.LocalDate

/**
 * TotalSettlementItemProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/28/23
 */
class TotalSettlementItemProcessor: ItemProcessor<SummingSettlementResponse, SettlementTotal> {
    override fun process(item: SummingSettlementResponse): SettlementTotal {
        return SettlementTotal(
            settlementDate = LocalDate.now(),
            sellerNo = item.sellerNo,
            sellerName = item.sellerName,
            sellerBusinessNumber = item.sellerBusinessNumber,
            taxType = item.taxType,
            sellType = item.sellType,
            pgSalesAmount = item.sumPgSalesAmount,
            couponDiscountAmount = item.sumCouponDiscountAmount,
            mileageUsageAmount = item.sumMileageUsageAmount,
            shippingFeeAmount = item.sumShippingFeeAmount,
            claimShippingFeeAmount = item.sumClaimShippingFeeAmount,
            commissionAmount = item.sumCommissionAmount,
            taxAmount = item.sumTasAmount,
        )
    }
}