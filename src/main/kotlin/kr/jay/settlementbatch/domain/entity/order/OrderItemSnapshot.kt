package kr.jay.settlementbatch.domain.entity.order
import jakarta.persistence.*
import kr.jay.settlementbatch.domain.entity.Product
import kr.jay.settlementbatch.domain.entity.Seller
import kr.jay.settlementbatch.domain.enums.TaxType
import kr.jay.settlementbatch.domain.enums.TaxTypeConverter
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
data class OrderItemSnapshot(
        @Id @Column(name = "order_item_snapshot_no") var id: Long,

        var createdAt: ZonedDateTime? = ZonedDateTime.now(),
        var updatedAt: ZonedDateTime? = ZonedDateTime.now(),
        var deletedAt: ZonedDateTime? = null,

        var productNo: Long,
        var sellerNo: Long,
        var sellPrice: BigDecimal? = BigDecimal.ZERO,
        var supplyPrice: BigDecimal? = BigDecimal.ZERO,
        var promotionAmount: BigDecimal? = BigDecimal.ZERO,
        var defaultDeliveryAmount: BigDecimal? = BigDecimal.valueOf(3000),
        val mileageUsageAmount: BigDecimal? = BigDecimal.ZERO,

        var itemCategory: Int? = 0, //TODO : Enum으로 변경
        var taxRate: Int? = 3,
        @Convert(converter = TaxTypeConverter::class)
        var taxType: TaxType? = TaxType.TAX,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="product_no")
        var product: Product,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="seller_no", referencedColumnName = "id", insertable = false, updatable = false)
        var seller: Seller,
)