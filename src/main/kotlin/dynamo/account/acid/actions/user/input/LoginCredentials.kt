package dynamo.account.acid.actions.user.input

import dynamo.account.acid.model.user.CredentialType

data class LoginCredentials(val username: String, val password: String, val type: CredentialType)