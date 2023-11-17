package kr.jay.settlementbatch.core.purchaseconfirm

import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider
import java.time.ZonedDateTime

/**
 * DeliveryCompletedJpaQueryProvider
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
class DeliveryCompletedJpaQueryProvider(
    private val startDateTime: ZonedDateTime,
    private val endDateTime: ZonedDateTime,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<OrderItem> = this.entityManager.createQuery(
            "SELECT oi " +
                    "FROM OrderItem oi " +
                    "LEFT OUTER JOIN ClaimReceipt cr ON oi.orderNo = cr.orderNo " +
                    "WHERE oi.shippedCompleteAt BETWEEN :startDateTime AND :endDateTime " +
                    "AND oi.purchaseConfirmedAt IS NULL " +
                    "AND (cr.orderNo IS NULL or cr.completedAt IS NOT NULL )",
            OrderItem::class.java
        )
        query.setParameter("startDateTime", startDateTime)
        query.setParameter("endDateTime", endDateTime)
        return query
    }

    override fun afterPropertiesSet() {

    }
}