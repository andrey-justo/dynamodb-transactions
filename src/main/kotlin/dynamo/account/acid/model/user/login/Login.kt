package dynamo.account.acid.model.user.login

import dynamo.account.acid.dao.UserDAO
import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType
import dynamo.account.acid.model.user.User
import java.lang.IllegalStateException

class Login(private val userDAO: UserDAO, val delegates: Map<CredentialType, LoginDelegate>) {

    fun execute(principal: String, accessToken: String, type: CredentialType): User {
        val credential = delegates[type]?.login(principal, accessToken) ?: throw IllegalStateException("No user found")
        return userDAO.get(credential.userId)
    }

}