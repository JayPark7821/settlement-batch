package kr.jay.settlementbatch.domain.entity.claim

/**
 * ClaimStatus
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
enum class ClaimStatus(val value: Int) {
    WITHDRAWN(0),
    RECEIVED(1),
    IN_PROGRESS(2),
    COMPLETED(3),
}