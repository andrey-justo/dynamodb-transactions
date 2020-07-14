package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.product.Grocery
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

class GroceryConverter: Converter<Grocery> {
  override fun load(doc: Map<String, AttributeValue>): Grocery {
    return Grocery(
      id = doc["id"]!!.s().toString(),
      imageUrl = doc["imageUrl"]!!.s().toString(),
      name = doc["name"]!!.s().toString(),
      price = doc["price"]?.n()?.toBigDecimal() ?: BigDecimal.ZERO,
      sku = doc["sku"]!!.s().toString(),
      stock = doc["stock"]?.n()?.toLong() ?: 0,
      calories = doc["calories"]!!.s().toString(),
      description = doc["description"]?.s().toString(),
      groceryId = doc["groceryId"]?.n()?.toLong() ?: 0,
      createdAt = doc["createdAt"]?.n()?.toLong()?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
      } ?: LocalDateTime.now()
    )
  }

  override fun convert(entity: Grocery): Map<String, AttributeValue> {
    return mapOf(
      "sku" to AttributeValue.builder().s(entity.sku).build(),
      "calories" to AttributeValue.builder().s(entity.calories).build(),
      "groceryId" to AttributeValue.builder().n(entity.groceryId.toString()).build()
    )
  }
}