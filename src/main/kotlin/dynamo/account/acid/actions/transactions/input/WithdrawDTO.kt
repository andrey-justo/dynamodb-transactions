package dynamo.account.acid.actions.transactions.input

import java.math.BigDecimal

data class WithdrawDTO(val amount: BigDecimal, val accountId: String)