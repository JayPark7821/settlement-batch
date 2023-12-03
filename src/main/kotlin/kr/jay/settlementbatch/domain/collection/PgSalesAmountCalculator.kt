package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.command.PgSalesAmountMaterial
import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import java.math.BigDecimal

/**
 * PgSalesAmountCalculator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/21/23
 */
class PgSalesAmountCalculator(
//    private val orderItemSnapshot: OrderItemSnapshot,
    private val pgSalesAmountMaterial: PgSalesAmountMaterial,
) {

    fun  getPgSalesAmount(): BigDecimal {
        val sellPrice = pgSalesAmountMaterial.sellPrice ?: BigDecimal.ZERO
        return sellPrice
            .subtract(pgSalesAmountMaterial.promotionAmount)
            .subtract(pgSalesAmountMaterial.mileageUsageAmount)
    }
}