package dynamo.account.acid.actions.product.input

import java.math.BigDecimal

data class GroceryDTO(
  val imageUrl: String,
  val name: String,
  val description: String,
  val sku: String,
  val stock: Long,
  val price: BigDecimal,
  val calories: String,
  val groceryId: Long = 0
)