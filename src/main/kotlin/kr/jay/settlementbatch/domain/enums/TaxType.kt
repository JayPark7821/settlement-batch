package kr.jay.settlementbatch.domain.enums

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.jay.settlementbatch.domain.entity.claim.ClaimStatus
import java.lang.IllegalArgumentException

/**
 * TaxType
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/20/23
 */
enum class TaxType(val value: String) {
    TAX("TAX"),
    ZERO("ZERO"),
    FREE("FREE"),
}

@Converter(autoApply = true)
class TaxTypeConverter : AttributeConverter<TaxType, String> {
    override fun convertToDatabaseColumn(attribute: TaxType?): String {
        return attribute?.value ?: throw IllegalArgumentException("Invalid ClaimStatus Status")
    }

    override fun convertToEntityAttribute(dbData: String?): TaxType {
        return when (dbData) {
            "TAX" -> TaxType.TAX
            "ZERO" -> TaxType.ZERO
            "FREE" -> TaxType.FREE
            else -> throw IllegalArgumentException("Invalid ClaimStatus Status")
        }
    }
}