package kr.jay.settlementbatch.domain.command

import java.math.BigDecimal

/**
 * PgSalesAmountMaterial
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/3/23
 */
data class PgSalesAmountMaterial(
    val sellPrice: BigDecimal? = BigDecimal.ZERO,
    val promotionAmount: BigDecimal? = BigDecimal.ZERO,
    val mileageUsageAmount: BigDecimal? = BigDecimal.ZERO,
)
