package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.collection.CommissionAmountCalculator
import kr.jay.settlementbatch.domain.collection.PgSalesAmountCalculator
import kr.jay.settlementbatch.domain.collection.PositiveDailySettlementCollection
import kr.jay.settlementbatch.domain.collection.TaxCalculator
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import org.springframework.batch.item.ItemProcessor
import java.math.BigDecimal
import java.time.LocalDate

/**
 * DailySettlementItemProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
class DailySettlementItemProcessor: ItemProcessor<OrderItem, SettlementDaily> {
    override fun process(item: OrderItem): SettlementDaily {
        val positiveDailySettlementCollection = PositiveDailySettlementCollection(item)
        return positiveDailySettlementCollection.getSettlementDaily()
    }
}