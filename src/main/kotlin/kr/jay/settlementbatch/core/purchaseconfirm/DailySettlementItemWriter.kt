package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import kr.jay.settlementbatch.infrastructure.database.repository.SettlementDailyRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Configuration

/**
 * DailySettlementItemWriter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/24/23
 */

class DailySettlementItemWriter(
    private val settlementDailyRepository: SettlementDailyRepository
): ItemWriter<SettlementDaily> {
    override fun write(chunk: Chunk<out SettlementDaily>) {
        for (item in chunk.items) {
            settlementDailyRepository.save(item)
        }
    }
}