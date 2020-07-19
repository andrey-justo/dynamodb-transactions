package dynamo.account.acid.model.user.signup

import dynamo.account.acid.dao.CredentialDAO
import dynamo.account.acid.dao.UserDAO
import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType
import dynamo.account.acid.model.user.User
import java.lang.IllegalArgumentException
import java.util.*

class SignUp(
    private val userDAO: UserDAO,
    private val credentialDAO: CredentialDAO,
    private val delegates: Map<CredentialType, ValidateCredentials> = mapOf(CredentialType.EMAIL to EmailValidateCredentials())
) {
    fun execute(principal: String, accessToken: String, type: CredentialType): User {
        val credentials = credentialDAO.find(principal)
        if (credentials != null) {
            throw IllegalArgumentException("User already exists")
        }

        val delegate: ValidateCredentials =
            delegates[type] ?: throw IllegalArgumentException("Credentials type is not valid")
        if (!delegate.validate(principal)) {
            throw IllegalArgumentException("Couldn't validate credentials")
        }

        val userId = UUID.randomUUID().toString()
        val credential = Credential(principal = principal, userId = userId, type = type)
        val user = User(userId = userId, credentials = listOf(credential))
        credentialDAO.add(credential, accessToken)
        userDAO.add(user)

        return user
    }
}