package kr.jay.settlementbatch.core.purchaseconfirm

import jakarta.persistence.EntityManager
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * DailySettlementItemReaderConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */

@Configuration
class DailySettlementItemReaderConfig(
    private val entityManager: EntityManager
) {

    private val chunkSize = 500
    private val startDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now(),
        LocalTime.MIN,
        ZoneId.of("Asia/Seoul")
    )
    private val endDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now(),
        LocalTime.MAX,
        ZoneId.of("Asia/Seoul")
    )

    @Bean
    fun dailySettlementJpaItemReader(): JpaPagingItemReader<OrderItem>{
        val customQueryProvider = CustomPurchaseConfirmedItemQueryProvider(
            this.startDateTime,
            this.endDateTime
        )
        return JpaPagingItemReaderBuilder<OrderItem>()
            .name("dailySettlementJpaItemReader")
            .entityManagerFactory(entityManager.entityManagerFactory)
            .pageSize(this.chunkSize)
            .queryProvider(customQueryProvider)
            .build()
    }
}