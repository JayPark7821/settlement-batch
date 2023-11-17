package kr.jay.settlementbatch.application

import kr.jay.settlementbatch.domain.ClaimCompleteService
import org.springframework.stereotype.Service

/**
 * ClaimCompleteFacade
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
@Service
class ClaimCompleteFacade (
    private val claimCompleteService: ClaimCompleteService,
){

    fun receiptComplete(id: Long) {
        claimCompleteService.complete(id)
    }
}