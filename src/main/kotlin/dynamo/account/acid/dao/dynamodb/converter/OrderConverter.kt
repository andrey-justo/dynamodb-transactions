package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.account.Order
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.LocalDateTime
import java.time.ZoneOffset

class OrderConverter : Converter<Order> {

  private val productConverter = ProductConverter()
  override fun load(doc: Map<String, AttributeValue>): Order {
    return Order(
      id = doc["id"]!!.n().toString(),
      date = doc["date"]?.n()?.toLong()?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
        ?: LocalDateTime.now(),
      accountId = doc["accountId"]!!.s().toString(),
      products = doc["products"]!!.l().map { productConverter.load(it.m()) }
    )
  }

  override fun convert(entity: Order): Map<String, AttributeValue> {
    return mapOf(
      "id" to AttributeValue.builder().n(entity.id).build(),
      "date" to AttributeValue.builder().n(entity.date.toEpochSecond(ZoneOffset.UTC).toString()).build(),
      "products" to AttributeValue.builder()
        .l(entity.products.map { AttributeValue.builder().m(productConverter.convert(it)).build() }).build(),
      "accountId" to AttributeValue.builder().n(entity.accountId.toString()).build()
    )
  }
}