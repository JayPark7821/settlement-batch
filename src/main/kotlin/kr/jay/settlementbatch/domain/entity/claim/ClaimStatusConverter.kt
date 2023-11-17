package kr.jay.settlementbatch.domain.entity.claim

import jakarta.persistence.AttributeConverter
import java.lang.IllegalArgumentException

/**
 * ClaimStatusConverter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */
class ClaimStatusConverter : AttributeConverter<ClaimStatus, Int> {
    override fun convertToDatabaseColumn(attribute: ClaimStatus?): Int {
        return attribute?.value ?: throw IllegalArgumentException("Invalid Claim Status")
    }

    override fun convertToEntityAttribute(dbData: Int?): ClaimStatus {
        return when (dbData) {
            0 -> ClaimStatus.WITHDRAWN
            1 -> ClaimStatus.RECEIVED
            2 -> ClaimStatus.IN_PROGRESS
            3 -> ClaimStatus.COMPLETED
            else -> throw IllegalArgumentException("Invalid Claim Status")
        }
    }
}