package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.settlement.SettlementDaily
import org.springframework.data.jpa.repository.JpaRepository

/**
 * SettlementDailyRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/24/23
 */
interface SettlementDailyRepository: JpaRepository<SettlementDaily, Long> {

}