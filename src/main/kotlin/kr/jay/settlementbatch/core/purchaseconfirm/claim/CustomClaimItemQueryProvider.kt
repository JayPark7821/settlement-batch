package kr.jay.settlementbatch.core.purchaseconfirm.claim

import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider

/**
 * CustomClaimItemQueryProvider
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/24/23
 */
class CustomClaimItemQueryProvider : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<ClaimItem> = this.entityManager.createQuery(
            "select ci " +
                    "from ClaimItem ci " +
                    "left outer join SettlementDaily sd on sd.claimReceiptNo = ci.claimReceiptNo " +
                    "join ClaimReceipt cr on ci.claimReceiptNo = cr.id " +
                    "where cr.completedAt is not null and sd.id is null ",
            ClaimItem::class.java
        )
        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}