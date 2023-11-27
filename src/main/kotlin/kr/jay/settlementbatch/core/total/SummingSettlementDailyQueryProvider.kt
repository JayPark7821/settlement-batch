package kr.jay.settlementbatch.core.total

import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider
import java.time.LocalDate

/**
 * SummingSettlementDailyQueryProvider
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/27/23
 */
class SummingSettlementDailyQueryProvider(
    private val startDate: LocalDate,
    private val endDate: LocalDate,
) : AbstractJpaQueryProvider() {
    override fun createQuery(): Query {
        val query: TypedQuery<SummingSettlementResponse> = this.entityManager.createQuery(
            "select sd.sellerNo, " +
                    "sd.sellerName, " +
                    "sd.sellerBusinessNumber, " +
                    "sd.taxType, " +
                    "sd.sellType, " +
                    "sum(sd.pgSalesAmount) as sumPgSalesAmount, " +
                    "sum(sd.couponDiscountAmount) as sumCouponDiscountAmount, " +
                    "sum(sd.mileageUsageAmount) as sumMileageUsageAmount, " +
                    "sum(sd.shippingFeeAmount) as sumShippingFeeAmount, " +
                    "sum(sd.claimShippingFeeAmount) as sumClaimShippingFeeAmount, " +
                    "sum(sd.commissionAmount) as sumCommissionAMount " +
                    "from SettlementDaily sd " +
                    "where sd.settlementDate between :startDate and :endDate " +
                    "group by sellerNo",
            SummingSettlementResponse::class.java
        )

        query.setParameter("startDate", this.startDate)
        query.setParameter("endDate", this.endDate)
        return query
    }

    override fun afterPropertiesSet() {
        TODO("Not yet implemented")
    }
}