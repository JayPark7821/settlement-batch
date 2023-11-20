package kr.jay.settlementbatch.core.purchaseconfirm

import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider
import java.time.ZonedDateTime

/**
 * CustomPurchaseConfirmedItemQueryProvider
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
class CustomPurchaseConfirmedItemQueryProvider(
    private val startDateTime: ZonedDateTime,
    private val endDateTime: ZonedDateTime
): AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<OrderItem > =
            this.entityManager.createQuery(
                "select oi " +
                        "from OrderItem oi " +
                        "where oi.purchaseConfirmedAt between :startDateTime and :endDateTime",
                OrderItem::class.java
            )
        query.setParameter("startDateTime", startDateTime)
        query.setParameter("endDateTime", endDateTime)

        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}