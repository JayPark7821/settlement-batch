package kr.jay.settlementbatch.core.purchaseconfirm

import jakarta.persistence.EntityManager
import kr.jay.settlementbatch.core.purchaseconfirm.claim.ClaimSettlementItemProcessor
import kr.jay.settlementbatch.core.purchaseconfirm.claim.ClaimSettlementItemWriter
import kr.jay.settlementbatch.domain.entity.claim.ClaimItem
import kr.jay.settlementbatch.domain.entity.order.OrderItem
import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import kr.jay.settlementbatch.infrastructure.database.repository.OrderItemRepository
import kr.jay.settlementbatch.infrastructure.database.repository.SettlementDailyRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.ZonedDateTime
import javax.sql.DataSource

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
    @Qualifier("claimSettlementJpaItemReader")
    private val claimSettlementJpaItemReader: JpaPagingItemReader<ClaimItem>,
    private val settlementDailyRepository: SettlementDailyRepository,
    private val datasource: DataSource,
    private val entityManager: EntityManager
) {
    private val JOB_NAME = "purchaseConfirmJob"
    private val chunkSize = 500

    @Bean
    fun purchaseConfirmJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(purchaseConfirmJobStep())
            .next(dailySettlementJobStep())
            .next(claimSettlementJobStep())
            .build()

    }


    @Bean
    @JobScope
    fun purchaseConfirmJobStep(): Step {
        return StepBuilder(JOB_NAME + "_step", jobRepository)
            .chunk<OrderItem, OrderItem>(this.chunkSize, transactionManager)
            .reader(deliveryCompletedJpaItemReader)
            .processor(purchaseConfirmProcessor())
//            .writer(purchaseConfirmItemWriter())
            .writer(purchaseConfirmedJdbcItemWriter())
//            .writer(purchaseConfirmedJpaItemWriter())
            .listener(PurchaseConfirmedChunkListener())
            .allowStartIfComplete(true)
            .build()
    }

    @Bean
    fun purchaseConfirmProcessor(): PurchaseConfirmProcessor {
        return PurchaseConfirmProcessor()
    }

    @Bean
    fun purchaseConfirmedJdbcItemWriter(): JdbcBatchItemWriter<OrderItem> {
        val writer = JdbcBatchItemWriterBuilder<OrderItem>()
            .dataSource(datasource)
            .sql(
                "update order_item " +
                        "set purchase_confirmed_at = :purchasedConfirmedAt, " +
                        "updated_at = :updatedAt " +
                        "where order_item_no = :id"
            )
            .itemSqlParameterSourceProvider { it ->
                val parameterSource = MapSqlParameterSource()
                parameterSource.addValue("purchasedConfirmedAt", ZonedDateTime.now())
                parameterSource.addValue("updatedAt", ZonedDateTime.now())
                parameterSource.addValue("id", it.id)
                parameterSource
            }
            .build()

        return writer
    }

    @Bean
    fun purchaseConfirmedJpaItemWriter(): JpaItemWriter<OrderItem>{
        val writer = JpaItemWriter<OrderItem>()
        writer.setEntityManagerFactory(this.entityManager.entityManagerFactory)
        return writer
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
            .processor(dailySettlementItemProcessor())
            .writer(dailySettlementItemWriter())
            .allowStartIfComplete(true)
            .build()
    }

    @Bean
    fun dailySettlementItemProcessor(): DailySettlementItemProcessor {
        return DailySettlementItemProcessor()
    }

    @Bean
    fun dailySettlementItemWriter(): DailySettlementItemWriter {
        return DailySettlementItemWriter(settlementDailyRepository)
    }

    @Bean
    @JobScope
    fun claimSettlementJobStep(): Step {
        return StepBuilder(JOB_NAME + "_claimSettlement_step", jobRepository)
            .chunk<ClaimItem, SettlementDaily>(this.chunkSize, transactionManager)
            .reader(claimSettlementJpaItemReader)
            .processor(claimSettlementItemProcessor())
            .writer(claimSettlementItemWriter())
            .build()
    }

    @Bean
    fun claimSettlementItemProcessor(): ClaimSettlementItemProcessor {
        return ClaimSettlementItemProcessor()
    }

    @Bean
    fun claimSettlementItemWriter(): ClaimSettlementItemWriter {
        return ClaimSettlementItemWriter(settlementDailyRepository)
    }
}
