package kr.jay.settlementbatch.core.total

import kr.jay.settlementbatch.domain.entity.settlement.SettlementTotal
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.LocalDate

/**
 * TotalSettlementJobConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/27/23
 */

@Configuration
@EnableTransactionManagement
class TotalSettlementJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    @Qualifier("totalSettlementJpaItemReader")
    private val totalSettlementJpaPagingItemReader: JpaPagingItemReader<SummingSettlementResponse>
) {

    private val JOB_NAME = "totalSettlementJob"
    private val chunkSize = 500

    @Bean
    fun totalSettlementJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(totalSettlementJobStep())
            .build()
    }

    @Bean
    @JobScope
    fun totalSettlementJobStep(): Step{
        return StepBuilder(JOB_NAME+"_step", jobRepository)
            .chunk<SummingSettlementResponse, SettlementTotal>(this.chunkSize, this.transactionManager)
            .reader(totalSettlementJpaPagingItemReader)
            .processor(totalSettlementItemProcessor())
            .build()
     }

    @Bean
    fun totalSettlementItemProcessor() = TotalSettlementItemProcessor()
}