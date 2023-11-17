package kr.jay.settlementbatch.infrastructure.database.repository

import kr.jay.settlementbatch.domain.ClaimCompleteExecutor
import kr.jay.settlementbatch.domain.entity.claim.ClaimStatus
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

/**
 * ClaimCompleteJpaExecutor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
@Service
class ClaimCompleteJpaExecutor (
    private val claimReceiptRepository: ClaimReceiptRepository,
): ClaimCompleteExecutor{
    override fun updateCompleteAt(id: Long) {
        val receipt = claimReceiptRepository.findById(id).get()
        claimReceiptRepository.save(
            receipt.copy(
                id = id,
                completedAt = ZonedDateTime.now(),
                claimStatus = ClaimStatus.COMPLETED
            )
        )
    }
}