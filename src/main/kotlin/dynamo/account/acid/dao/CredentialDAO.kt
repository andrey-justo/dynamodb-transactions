package dynamo.account.acid.dao

import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType

interface CredentialDAO {

    fun check(principal: String, type: CredentialType, accessToken: String): Credential
    fun add(credential: Credential, accessToken: String)
    fun find(principal: String): Credential?

}