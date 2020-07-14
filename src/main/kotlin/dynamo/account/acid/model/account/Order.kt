package dynamo.account.acid.model.account

import dynamo.account.acid.model.product.Product
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Order(
  override val id: String = UUID.randomUUID().toString(),
  override val accountId: String,
  override val date: LocalDateTime = LocalDateTime.now(),
  val products: List<Product>
) : Transaction(accountId = accountId, id = id, date = date) {
  override fun type() = Type.ORDER
  fun total() = products.fold(BigDecimal.ZERO) { acc, it -> acc + it.price }
}
