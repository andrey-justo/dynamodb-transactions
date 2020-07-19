package dynamo.account.acid.model.user.signup

import dynamo.account.acid.model.user.CredentialType

abstract class ValidateCredentials(type: CredentialType) {
    abstract fun validate(principal: String): Boolean
}