package dynamo.account.acid.model.account

import java.math.BigDecimal

data class Order(override val account: Account, val amount: BigDecimal) : Transaction(account = account) {
    override fun type(): String = "ORDER"
}
