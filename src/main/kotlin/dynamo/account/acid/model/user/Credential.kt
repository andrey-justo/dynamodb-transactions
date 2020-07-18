package dynamo.account.acid.model.user

import dynamo.account.acid.model.user.login.LoginType

data class Credential(val principal: String, val userId: String, val type: LoginType)