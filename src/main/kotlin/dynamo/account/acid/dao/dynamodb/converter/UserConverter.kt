package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.user.User
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.LocalDateTime
import java.time.ZoneOffset

class UserConverter : Converter<User> {
    override fun load(doc: Map<String, AttributeValue>): User {
        return User(
            userId = doc["id"]!!.s().toString(),
            credentials = emptyList(),
            isActive = doc["isActive"]?.bool() ?: true,
            createdAt = doc["createdAt"]?.n()?.toLong()?.let {
                LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
            } ?: LocalDateTime.now()
        )
    }

    override fun convert(entity: User): Map<String, AttributeValue> {
        return mapOf(
            "userId" to AttributeValue.builder().s(entity.userId).build(),
            "isActive" to AttributeValue.builder().bool(entity.isActive).build(),
            "createdAt" to AttributeValue.builder().n(entity.createdAt.toEpochSecond(ZoneOffset.UTC).toString()).build()
        )
    }
}