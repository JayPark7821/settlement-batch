package kr.jay.settlementbatch.core.total

import kr.jay.settlementbatch.domain.entity.settlement.SettlementTotal
import kr.jay.settlementbatch.infrastructure.database.repository.SettlementTotalRepository
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter

/**
 * TotalSettlementItemWriter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/2/23
 */
class TotalSettlementItemWriter(
    private val settlementTotalRepository: SettlementTotalRepository
): ItemWriter<SettlementTotal> {
    override fun write(chunk: Chunk<out SettlementTotal>) {
        for (item in chunk.items) {
            settlementTotalRepository.save(item)
        }
    }
}