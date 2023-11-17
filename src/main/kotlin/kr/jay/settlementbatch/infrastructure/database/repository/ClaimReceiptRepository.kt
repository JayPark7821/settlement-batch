package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.entity.claim.ClaimReceipt
import org.springframework.data.jpa.repository.JpaRepository

/**
 * ClaimReceiptRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
interface ClaimReceiptRepository: JpaRepository<ClaimReceipt, Long>
