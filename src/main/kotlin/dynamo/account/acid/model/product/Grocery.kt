package dynamo.account.acid.model.product

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Grocery(
  override val id: String = UUID.randomUUID().toString(),
  override val price: BigDecimal,
  override val description: String,
  override val name: String,
  override val imageUrl: String,
  override val stock: Long,
  override val createdAt: LocalDateTime = LocalDateTime.now(),
  val calories: String,
  val groceryId: Long,
  val sku: String
) : Product(
  id = id,
  name = name,
  description = description,
  imageUrl = imageUrl,
  price = price,
  stock = stock,
  type = Type.GROCERY
)