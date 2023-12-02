package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.settlement.SettlementTotal
import org.springframework.data.jpa.repository.JpaRepository

/**
 * SettlementTotalRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/2/23
 */
interface SettlementTotalRepository : JpaRepository<SettlementTotal, Long> {
}