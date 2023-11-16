package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * PurchaseConfirmWriter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/16/23
 */
@Component
@Transactional
class PurchaseConfirmWriter (
    private val orderItemRepository: OrderItemRepository,
): ItemWriter<OrderItem> {
    override fun write(chunk: Chunk<out OrderItem>) {
        for (item in chunk.items){
            orderItemRepository.save(item)
        }
    }

}