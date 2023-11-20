package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.TaxTypeItem
import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import kr.jay.settlementbatch.domain.enums.TaxType
import java.math.BigDecimal

/**
 * TaxCalculator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
class TaxCalculator(
    private val orderItemSnapshot: OrderItemSnapshot,
) {
    private val TAX_RATE = 0.03

    fun getTaxTypeItem(): TaxTypeItem {
        return when(orderItemSnapshot.taxType){
            TaxType.TAX -> TaxTypeItem.TaxItem(orderItemSnapshot.sellPrice)
            TaxType.FREE -> TaxTypeItem.TaxFreeItem(orderItemSnapshot.sellPrice)
            TaxType.ZERO -> TaxTypeItem.ZeroTaxItem(orderItemSnapshot.sellPrice)
            else -> TaxTypeItem.TaxItem(orderItemSnapshot.sellPrice)
        }
    }

    fun getTaxAmount(): BigDecimal {
        val taxTypeItem = getTaxTypeItem()
        return when(taxTypeItem){
            is TaxTypeItem.TaxItem -> getCalculateTaxForTaxItem(taxTypeItem)
            is TaxTypeItem.ZeroTaxItem -> taxTypeItem.price?: BigDecimal.ZERO
            is TaxTypeItem.TaxFreeItem -> taxTypeItem.price?: BigDecimal.ZERO
            else -> BigDecimal.ZERO
        }
    }

    private fun getCalculateTaxForTaxItem(taxTypeItem: TaxTypeItem.TaxItem): BigDecimal {
        val price = taxTypeItem.price ?: BigDecimal.ZERO
        val rate = BigDecimal(TAX_RATE)
        return price.multiply(rate)
    }
}