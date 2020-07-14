package dynamo.account.acid.model.account

import java.math.BigDecimal
import java.time.LocalDateTime

data class Account(
  val id: String,
  val userId: Long,
  val balance: BigDecimal = BigDecimal.ZERO,
  val limits: BigDecimal,
  val createdAt: LocalDateTime = LocalDateTime.now()
)
