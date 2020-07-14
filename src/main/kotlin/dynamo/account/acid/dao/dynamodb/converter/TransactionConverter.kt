package dynamo.account.acid.dao.dynamodb.converter

import com.sun.org.apache.xpath.internal.operations.Or
import dynamo.account.acid.model.account.Deposit
import dynamo.account.acid.model.account.Order
import dynamo.account.acid.model.account.Transaction
import dynamo.account.acid.model.account.Withdraw
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.ZoneOffset

class TransactionConverter : Converter<Transaction> {

  private val depositConverter = DepositConverter()
  private val withdrawConverter = WithdrawConverter()
  private val orderConverter = OrderConverter()
  override fun load(doc: Map<String, AttributeValue>): Transaction {
    return when (doc["type"]?.s()?.toString()?.let { Transaction.Type.valueOf(it) }
      ?: throw NoSuchElementException("doc without type, $doc")) {
      Transaction.Type.ORDER -> orderConverter.load(doc)
      Transaction.Type.DEPOSIT -> depositConverter.load(doc)
      Transaction.Type.WITHDRAW -> withdrawConverter.load(doc)
    }
  }

  override fun convert(entity: Transaction): Map<String, AttributeValue> {
    val defaultValues = mapOf(
      "id" to AttributeValue.builder().n(entity.id).build(),
      "accountId" to AttributeValue.builder().n(entity.accountId.toString()).build(),
      "type" to AttributeValue.builder().n(entity.type().toString()).build(),
      "date" to AttributeValue.builder().n(entity.date.toEpochSecond(ZoneOffset.UTC).toString()).build()
    )

    return when (entity.type()) {
      Transaction.Type.ORDER -> defaultValues + orderConverter.convert(entity as Order)
      Transaction.Type.DEPOSIT -> defaultValues + depositConverter.convert(entity as Deposit)
      Transaction.Type.WITHDRAW -> defaultValues + withdrawConverter.convert(entity as Withdraw)
    }
  }
}