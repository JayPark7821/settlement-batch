package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder
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
class DeliveryCompletedItemReaderConfig {

    private val chunkSize = 500
    private val startDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now(),
        LocalTime.MIN,
        ZoneId.of("Asia/Seoul")
    )
    private val endDateTime: ZonedDateTime = ZonedDateTime.of(
        LocalDate.now().plusDays(1),
        LocalTime.MIN,
        ZoneId.of("Asia/Seoul")
    )

     @Bean
     fun deliveryCompletedJpaItemReader(orderItemRepository: OrderItemRepository): RepositoryItemReader<OrderItem> {
         return RepositoryItemReaderBuilder<OrderItem>()
             .name("deliveryCompletedJpaItemReader")
             .repository(orderItemRepository)
             .methodName("findByShippedCompleteAtBetween")
             .arguments(startDateTime, endDateTime)
             .pageSize(this.chunkSize)
             .sorts(mapOf("shippedCompleteAt" to Sort.Direction.ASC))
             .build()
     }
}