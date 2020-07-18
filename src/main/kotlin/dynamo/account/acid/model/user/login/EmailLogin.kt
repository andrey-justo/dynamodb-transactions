package dynamo.account.acid.model.user.login

import dynamo.account.acid.dao.CredentialDAO
import dynamo.account.acid.model.user.Credential

class EmailLogin(private val credentialDao: CredentialDAO): Login(type = LoginType.EMAIL) {
    override fun login(credential: Credential, accessToken: String): Boolean {
        return credentialDao.check(credential.principal, type, accessToken)
    }
}