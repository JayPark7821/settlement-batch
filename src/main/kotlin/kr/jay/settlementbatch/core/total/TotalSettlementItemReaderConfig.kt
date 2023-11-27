package kr.jay.settlementbatch.core.total

import jakarta.persistence.EntityManager
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

/**
 * TotalSettlementItemReaderConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/27/23
 */

@Configuration
class TotalSettlementItemReaderConfig(
    private val entityManager: EntityManager,
) {
    private val chunkSize = 500
    private val currentDate: LocalDate = LocalDate.now()

   @Bean
   fun totalSettlementJpaItemReader(orderItemRepository: OrderItemRepository): JpaPagingItemReader<SummingSettlementResponse>{
       val queryProvider = SummingSettlementDailyQueryProvider(getFirstDate(), getLastDate())
       return JpaPagingItemReaderBuilder<SummingSettlementResponse>()
           .name("totalSettlementJpaItemReader")
           .entityManagerFactory(entityManager.entityManagerFactory)
           .pageSize(chunkSize)
           .queryProvider(queryProvider )
           .build()
   }

    private fun getFirstDate(): LocalDate{
        val year= this.currentDate.year
        val month = this.currentDate.month

        return if(month.value == 1){
            LocalDate.of(year-1, 12, 1)
        }else{
            LocalDate.of(year, month.minus(1), 1)
        }
    }

    private fun getLastDate(): LocalDate{
        return this.currentDate.withDayOfMonth(1).minusDays(1)
    }
}