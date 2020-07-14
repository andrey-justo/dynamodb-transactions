package dynamo.account.acid.model.product

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

abstract class Product(
  open val id: String = UUID.randomUUID().toString(),
  open val price: BigDecimal,
  open val description: String,
  open val name: String,
  open val imageUrl: String,
  open val stock: Long,
  open val createdAt: LocalDateTime = LocalDateTime.now(),
  open val type: Type
) {
  enum class Type {
    GROCERY,
    MEDICINE
  }
}