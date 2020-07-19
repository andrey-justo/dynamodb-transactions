package dynamo.account.acid.dao.dynamodb.credential

import dynamo.account.acid.model.user.CredentialType

data class CredentialDTO(val principal: String, val type: CredentialType, val secret: String, val userId: String)