package dynamo.account.acid.model.user

data class Credential(val principal: String, val userId: String, val type: CredentialType)