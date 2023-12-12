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
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "order_item_snapshot_no")
        val id: Long? = null,

        val createdAt: ZonedDateTime? = ZonedDateTime.now(),
        val updatedAt: ZonedDateTime? = ZonedDateTime.now(),
        val deletedAt: ZonedDateTime? = null,

        @Column(name = "product_no")
        val productNo: Long,

        @Column(name = "seller_no")
        val sellerNo: Long,

        val sellPrice: BigDecimal? = BigDecimal.ZERO,
        val supplyPrice: BigDecimal? = BigDecimal.ZERO,
        val promotionAmount: BigDecimal? = BigDecimal.ZERO,
        val defaultDeliveryAmount: BigDecimal? = BigDecimal.valueOf(3000),
        val mileageUsageAmount: BigDecimal? = BigDecimal.ZERO,

        val itemCategory: Int? = 0, //TODO : Enum으로 변경
        val taxRate: Int? = 3,

        @Convert(converter = TaxTypeConverter::class)
        val taxType: TaxType? = TaxType.TAX,

        @ManyToOne
        @JoinColumn(name = "seller_no", insertable = false, updatable = false)
        val seller: Seller,

        @ManyToOne
        @JoinColumn(name = "product_no", insertable = false, updatable = false)
        val product: Product,
)