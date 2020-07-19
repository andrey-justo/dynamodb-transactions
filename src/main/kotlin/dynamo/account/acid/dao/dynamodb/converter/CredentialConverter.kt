package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType
import dynamo.account.acid.model.user.User
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.LocalDateTime
import java.time.ZoneOffset

class CredentialConverter : Converter<Credential> {
    override fun load(doc: Map<String, AttributeValue>): Credential {
        return Credential(
            userId = doc["id"]!!.s().toString(),
            type = CredentialType.valueOf(doc["type"]!!.s().toString()),
            principal = doc["principal"]!!.s().toString()
        )
    }

    override fun convert(entity: Credential): Map<String, AttributeValue> {
        return mapOf(
            "userId" to AttributeValue.builder().s(entity.userId).build(),
            "principal" to AttributeValue.builder().s(entity.principal).build(),
            "type" to AttributeValue.builder().s(entity.type.name).build()
        )
    }
}