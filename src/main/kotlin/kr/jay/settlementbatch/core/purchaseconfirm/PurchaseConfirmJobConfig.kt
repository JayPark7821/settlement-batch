package kr.jay.settlementbatch.core.purchaseconfirm

import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * PurchaseConfirmJobConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/16/23
 */
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
class PurchaseConfirmJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val orderItemRepository: OrderItemRepository,
    @Qualifier("deliveryCompletedJpaItemReader")
    private val deliveryCompletedJpaItemReader: JpaPagingItemReader<OrderItem>,
    @Qualifier("dailySettlementJpaItemReader")
    private val dailySettlementJpaItemReader: JpaPagingItemReader<OrderItem>,
) {
    private val JOB_NAME = "purchaseConfirmJob"
    private val chunkSize = 500

    @Bean
    fun purchaseConfirmJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(purchaseConfirmJobStep())
            .next(dailySettlementJobStep())
            .build()

    }


    @Bean
    @JobScope
    fun purchaseConfirmJobStep(): Step {
        return StepBuilder(JOB_NAME + "_step", jobRepository)
            .chunk<OrderItem, OrderItem>(this.chunkSize, transactionManager)
            .reader(deliveryCompletedJpaItemReader)
            .processor(purchaseConfirmProcessor())
            .writer(purchaseConfirmItemWriter())
            .build()
    }

    @Bean
    fun purchaseConfirmProcessor(): PurchaseConfirmProcessor {
        return PurchaseConfirmProcessor()
    }

    @Bean
    fun purchaseConfirmItemWriter(): PurchaseConfirmWriter {
        return PurchaseConfirmWriter(orderItemRepository)
    }

    @Bean
    @JobScope
    fun dailySettlementJobStep(): Step {
        return StepBuilder(JOB_NAME + "_dailySettlement_step", jobRepository)
            .chunk<OrderItem, SettlementDaily>(this.chunkSize, transactionManager)
            .reader(dailySettlementJpaItemReader)
            .build()
    }

    @Bean
    fun dailySettlementItemProcessor(): DailySettlementItemProcessor{
        return DailySettlementItemProcessor()
    }
}
