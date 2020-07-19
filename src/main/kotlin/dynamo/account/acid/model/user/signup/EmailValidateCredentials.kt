package dynamo.account.acid.model.user.signup

import dynamo.account.acid.model.user.CredentialType

class EmailValidateCredentials: ValidateCredentials(type = CredentialType.EMAIL) {
    override fun validate(principal: String): Boolean {
        return true
    }
}