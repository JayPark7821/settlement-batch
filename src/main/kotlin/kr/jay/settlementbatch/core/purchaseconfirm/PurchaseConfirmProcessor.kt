package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.entity.order.OrderItem
import org.springframework.batch.item.ItemProcessor
import java.time.ZonedDateTime

/**
 * PurchaseConfirmProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/16/23
 */
class PurchaseConfirmProcessor: ItemProcessor<OrderItem, OrderItem> {
    override fun process(item: OrderItem): OrderItem {
        return item.copy(id = item.id, purchaseConfirmedAt = ZonedDateTime.now())
    }
}