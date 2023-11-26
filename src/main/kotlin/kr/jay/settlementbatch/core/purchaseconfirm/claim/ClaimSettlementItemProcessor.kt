package kr.jay.settlementbatch.core.purchaseconfirm.claim

import kr.jay.settlementbatch.domain.collection.NegativeDailySettlementCollection
import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import org.springframework.batch.item.ItemProcessor

/**
 * ClaimSettlementItemProcessor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/26/23
 */
class ClaimSettlementItemProcessor : ItemProcessor<ClaimItem, SettlementDaily>{

    override fun process(item: ClaimItem): SettlementDaily {
        return NegativeDailySettlementCollection(item).getSettlementDaily()
    }

}