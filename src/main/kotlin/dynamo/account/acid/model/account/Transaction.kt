package dynamo.account.acid.model.account

import java.time.LocalDateTime
import java.util.UUID

abstract class Transaction(
    open val id: String = UUID.randomUUID().toString(),
    open val accountId: String,
    open val date: LocalDateTime = LocalDateTime.now()
) {
    enum class Type {
        ORDER,
        DEPOSIT,
        WITHDRAW
    }
    abstract fun type(): Type
}
