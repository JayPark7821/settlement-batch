package kr.jay.settlementbatch.domain.entity.claim
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.ZonedDateTime

@Entity
data class ClaimReceipt(
    @Id @Column(name = "claim_receipt_no") val id : Long,
    val orderNo: Long,

    val createdAt: ZonedDateTime? = ZonedDateTime.now(),
    val updatedAt: ZonedDateTime? = ZonedDateTime.now(),
    val deletedAt: ZonedDateTime? = null,
    val completedAt: ZonedDateTime? = null,

    val requestType: String, //TODO ENUM으로
    @Convert(converter = ClaimStatusConverter::class)
    val claimStatus: ClaimStatus, //TODO ENUM으로
    val extraFeePayer: Int, //TODO ENUM으로
    val claimReason: Int, //TODO ENUM으로
)