package dynamo.account.acid.model.user.login

import dynamo.account.acid.dao.CredentialDAO
import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType

class EmailLoginDelegate(private val credentialDao: CredentialDAO) : LoginDelegate(type = CredentialType.EMAIL) {
    override fun login(principal: String, accessToken: String): Credential {
        return credentialDao.check(principal, type, accessToken)
    }
}