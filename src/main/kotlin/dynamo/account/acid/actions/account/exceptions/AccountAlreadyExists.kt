package dynamo.account.acid.actions.account.exceptions

import java.lang.RuntimeException

class AccountAlreadyExists(message: String): RuntimeException(message)