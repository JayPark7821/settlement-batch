package kr.jay.settlementbatch.core.purchaseconfirm.claim

import jakarta.persistence.EntityManager
import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ClaimSettlementItemReaderConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/24/23
 */
@Configuration
class ClaimSettlementItemReaderConfig(
    private val entityManager: EntityManager
) {
    private val chunkSize = 100

    @Bean
    fun claimSettlementJpaItemReader(): JpaPagingItemReader<ClaimItem> {
        val queryProvider = CustomClaimItemQueryProvider()
        return JpaPagingItemReaderBuilder<ClaimItem>()
            .name("claimSettlementJpaItemReader")
            .entityManagerFactory(this.entityManager.entityManagerFactory)
            .pageSize(chunkSize)
            .queryProvider(queryProvider)
            .build()
    }
}