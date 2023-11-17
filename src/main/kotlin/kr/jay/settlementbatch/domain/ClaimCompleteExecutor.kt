package kr.jay.settlementbatch.domain

/**
 * ClaimCompleteExecutor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
interface ClaimCompleteExecutor {
    fun updateCompleteAt(id: Long)
}