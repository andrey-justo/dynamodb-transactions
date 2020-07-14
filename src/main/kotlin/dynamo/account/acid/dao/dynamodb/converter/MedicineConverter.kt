package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.product.Grocery
import dynamo.account.acid.model.product.Medicine
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

class MedicineConverter: Converter<Medicine> {
  override fun load(doc: Map<String, AttributeValue>): Medicine {
    return Medicine(
      id = doc["id"]!!.s().toString(),
      imageUrl = doc["imageUrl"]!!.s().toString(),
      name = doc["name"]!!.s().toString(),
      price = doc["price"]?.n()?.toBigDecimal() ?: BigDecimal.ZERO,
      attachedPrescriptionUrl = doc["attachedPrescriptionUrl"]!!.s().toString(),
      stock = doc["stock"]?.n()?.toLong() ?: 0,
      barCode = doc["barCode"]!!.s().toString(),
      description = doc["description"]?.s().toString(),
      needsPrescription = doc["needsPrescription"]?.bool() ?: false,
      createdAt = doc["createdAt"]?.n()?.toLong()?.let {
        LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
      } ?: LocalDateTime.now()
    )
  }

  override fun convert(entity: Medicine): Map<String, AttributeValue> {
    return mapOf(
      "attachedPrescriptionUrl" to AttributeValue.builder().s(entity.attachedPrescriptionUrl).build(),
      "barCode" to AttributeValue.builder().s(entity.barCode).build(),
      "needsPrescription" to AttributeValue.builder().n(entity.needsPrescription.toString()).build()
    )
  }
}