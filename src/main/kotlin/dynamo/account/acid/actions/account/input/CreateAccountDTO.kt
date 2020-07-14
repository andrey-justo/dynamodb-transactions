package dynamo.account.acid.actions.account.input

import java.math.BigDecimal

data class CreateAccountDTO(val userId: Long, val limits: BigDecimal)