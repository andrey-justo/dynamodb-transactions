package dynamo.account.acid.model.account

import java.time.LocalDateTime
import java.util.UUID

abstract class Transaction(
    val id: String = UUID.randomUUID().toString(),
    open val account: Account,
    val date: LocalDateTime = LocalDateTime.now()
) {
    abstract fun type(): String
}
