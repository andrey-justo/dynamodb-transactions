package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.account.Deposit
import dynamo.account.acid.model.account.Withdraw
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

class WithdrawConverter : Converter<Withdraw> {

  override fun load(doc: Map<String, AttributeValue>): Withdraw {
    return Withdraw(
      id = doc["id"]!!.n().toString(),
      date = doc["date"]?.n()?.toLong()?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
        ?: LocalDateTime.now(),
      accountId = doc["accountId"]!!.s().toString(),
      amount = doc["amount"]?.n()?.toString()?.let { BigDecimal(it) }
        ?: throw NoSuchElementException("No amount for $doc")
    )
  }

  override fun convert(entity: Withdraw): Map<String, AttributeValue> {
    return mapOf(
      "id" to AttributeValue.builder().n(entity.id).build(),
      "date" to AttributeValue.builder().n(entity.date.toEpochSecond(ZoneOffset.UTC).toString()).build(),
      "amount" to AttributeValue.builder().n(entity.amount.toString()).build(),
      "accountId" to AttributeValue.builder().n(entity.accountId.toString()).build()
    )
  }
}