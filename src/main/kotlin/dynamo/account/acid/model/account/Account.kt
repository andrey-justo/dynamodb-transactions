package dynamo.account.acid.model.account

import java.math.BigDecimal
import java.time.LocalDateTime

data class Account(val id: Long, val userId: Long, val balance: BigDecimal, val limits: BigDecimal, val createdAt: LocalDateTime)
