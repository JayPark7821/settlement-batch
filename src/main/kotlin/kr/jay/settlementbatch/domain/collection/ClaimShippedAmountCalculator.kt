package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import kr.jay.settlementbatch.domain.entity.claim.ClaimType
import kr.jay.settlementbatch.domain.entity.claim.ExtraFeePayer
import java.math.BigDecimal

/**
 * ClaimShippedAmountCalculator
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/26/23
 */
class ClaimShippedAmountCalculator(
    private val item: ClaimItem
) {

    private val SHIPPING_AMOUNT : BigDecimal = BigDecimal.valueOf(3000L)

    fun getClaimShippedAmount() : BigDecimal{
        val claimReceipt = item.claimReceipt
        val shippingCount = getShippingCount(claimReceipt.requestType, claimReceipt.extraFeePayer)

        return SHIPPING_AMOUNT.multiply(shippingCount.toBigDecimal())
    }

    private fun getShippingCount(requestType: ClaimType, extraFeePayer: ExtraFeePayer): Int{
        if(extraFeePayer == ExtraFeePayer.BUYER){
            return 0
        }
        return when(requestType){
            ClaimType.CANCELED->0
            ClaimType.EXCHANGE->0
            ClaimType.RETURN->0

        }
    }
}