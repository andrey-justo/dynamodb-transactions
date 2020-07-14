package dynamo.account.acid.model.account

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Deposit(
  override val id: String = UUID.randomUUID().toString(),
  override val date: LocalDateTime = LocalDateTime.now(),
  override val accountId: String,
  val amount: BigDecimal
) :
  Transaction(accountId = accountId, id = id, date = date) {
  override fun type() = Type.DEPOSIT
}
