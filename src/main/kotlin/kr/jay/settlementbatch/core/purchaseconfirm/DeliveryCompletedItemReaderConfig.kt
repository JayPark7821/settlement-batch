package kr.jay.settlementbatch.core.purchaseconfirm

import jakarta.persistence.EntityManager
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone

/**
 * DeliveryCompletedItemReaderConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/16/23
 */

@Configuration
class DeliveryCompletedItemReaderConfig(
    private val entityManager: EntityManager,
) {

    val chunkSize = 500
    val startDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now().minusDays(1),
        LocalTime.MIN,
        ZoneId.of("Asia/Seoul"))

    val endDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now().plusDays(1),
        LocalTime.MIN,
        ZoneId.of("Asia/Seoul"))

    @Bean
    fun deliveryCompletedJpaItemReader(): JpaPagingItemReader<OrderItem> {

        val queryProvider = DeliveryCompletedJpaQueryProvider(this.startDateTime, this.endDateTime)

        return JpaPagingItemReaderBuilder<OrderItem>()
            .name("deliveryCompletedJpaItemReader")
            .entityManagerFactory(this.entityManager.entityManagerFactory) // EntityManagerFactory 주입
            .pageSize(this.chunkSize)
            .queryProvider(queryProvider)
            .build()
    }
}