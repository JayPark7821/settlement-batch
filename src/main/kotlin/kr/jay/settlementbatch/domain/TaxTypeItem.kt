package kr.jay.settlementbatch.domain

import java.math.BigDecimal

/**
 * TaxTypeItem
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
sealed class TaxTypeItem {
    data class TaxItem(
        val price: BigDecimal? = BigDecimal.ZERO
    ): TaxTypeItem()
    data class ZeroTaxItem(
        val price: BigDecimal? = BigDecimal.ZERO
    ): TaxTypeItem()
    data class TaxFreeItem(
        val price: BigDecimal? = BigDecimal.ZERO
    ): TaxTypeItem()
}