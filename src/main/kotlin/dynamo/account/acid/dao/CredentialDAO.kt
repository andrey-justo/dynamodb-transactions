package dynamo.account.acid.dao

import dynamo.account.acid.model.user.login.LoginType

interface CredentialDAO {

    fun check(principal: String, type: LoginType, accessToken: String): Boolean

}