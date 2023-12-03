package kr.jay.settlementbatch.domain.collection

import kr.jay.settlementbatch.domain.command.PgSalesAmountMaterial
import kr.jay.settlementbatch.domain.entity.Product
import kr.jay.settlementbatch.domain.entity.Seller
import kr.jay.settlementbatch.domain.entity.order.OrderItemSnapshot
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * PgSalesAmountCalculatorTest
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/3/23
 */

@DisplayName("pg 금액을 계산하는 Test")
class PgSalesAmountCalculatorTest {
    private val sampleItemSnapshot = OrderItemSnapshot(
        1,
        sellerNo = 1,
        productNo = 1,
        sellPrice = BigDecimal.valueOf(10000.000),
        supplyPrice = BigDecimal.valueOf(8000.000),
        promotionAmount = BigDecimal.valueOf(0.000),
        mileageUsageAmount = BigDecimal.ZERO,
        seller = Seller(1, "니모", sellType = "P"),
        product = Product(
            1, productName = "어항1", sellerNo = 1,
            sellPrice = BigDecimal.valueOf(10000.000),
            supplyPrice = BigDecimal.valueOf(8000.000),
            category = 1
        ),
    )

    @DisplayName("프로모션(쿠폰)도 없고, 적립금 사용도 없는 경우")
    @Test
    fun test1() {
        val calculator = PgSalesAmountCalculator(sampleItemSnapshot)
        val pgSalesAmount = calculator.getPgSalesAmount()

        Assertions.assertEquals(pgSalesAmount, BigDecimal.valueOf(10000.000))
    }
}