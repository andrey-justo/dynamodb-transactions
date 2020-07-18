package dynamo.account.acid.model.user.login

import dynamo.account.acid.model.user.Credential

abstract class Login(val type: LoginType) {
    abstract fun login(credential: Credential, accessToken: String): Boolean
}