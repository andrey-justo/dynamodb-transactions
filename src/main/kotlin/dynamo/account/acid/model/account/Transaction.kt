package dynamo.account.acid.model.account

import java.time.LocalDateTime

abstract class Transaction(open val account: Account, val date: LocalDateTime = LocalDateTime.now()) {
    abstract fun type(): String
}
