package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import java.math.BigDecimal

/**
 * CommissionAmountCalculator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/21/23
 */
class CommissionAmountCalculator(
    private val orderItemSnapshot: OrderItemSnapshot,
) {

    fun getCommissionAmount(): BigDecimal {
        val seller = orderItemSnapshot.seller
        // 마진 금액( 판매가 - 공급가 )

        val price = orderItemSnapshot.sellPrice ?: BigDecimal.ZERO
        val marginAmount = price.subtract(orderItemSnapshot.supplyPrice)

        // 수수료 비율
        val commission = seller.commission ?: 0

        return getCalculate(marginAmount, commission)
    }

    private fun getCalculate(marginAmount: BigDecimal, commission: Int): BigDecimal {
        return if (commission == 0) {
            BigDecimal.ZERO
        } else {
            marginAmount.multiply(commission.div(100).toBigDecimal())
        }

    }

}