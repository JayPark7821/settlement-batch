package kr.jay.settlementbatch.domain

import org.springframework.stereotype.Service

/**
 * ClaimCompleteService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
@Service
class ClaimCompleteService (
    private val executoer: ClaimCompleteExecutor,
){
    fun complete(id: Long) {
        executoer.updateCompleteAt(id)
    }
}