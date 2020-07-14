package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.account.Account
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

class AccountConverter: Converter<Account> {
  override fun load(doc: Map<String, AttributeValue>): Account {
    return Account(
      id = doc["id"]!!.s().toString(),
      userId = doc["userId"]!!.n().toLong(),
      balance = doc["balance"]?.n()?.toBigDecimal() ?: BigDecimal.ZERO,
      limits = doc["limits"]?.n()?.toBigDecimal() ?: BigDecimal.ZERO,
      createdAt = doc["createdAt"]?.n()?.toLong()?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
      } ?: LocalDateTime.now()
    )
  }

  override fun convert(entity: Account): Map<String, AttributeValue> {
    return mapOf(
      "id" to AttributeValue.builder().s(entity.id).build(),
      "userId" to AttributeValue.builder().n(entity.userId.toString()).build(),
      "balance" to AttributeValue.builder().n(entity.balance.toString()).build(),
      "limits" to AttributeValue.builder().n(entity.limits.toString()).build(),
      "createdAt" to AttributeValue.builder().n(entity.createdAt.toEpochSecond(ZoneOffset.UTC).toString()).build()
    )
  }
}