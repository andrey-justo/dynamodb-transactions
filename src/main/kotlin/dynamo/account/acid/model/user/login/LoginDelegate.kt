package dynamo.account.acid.model.user.login

import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType

abstract class LoginDelegate(protected val type: CredentialType) {
    abstract fun login(principal: String, accessToken: String): Credential
}