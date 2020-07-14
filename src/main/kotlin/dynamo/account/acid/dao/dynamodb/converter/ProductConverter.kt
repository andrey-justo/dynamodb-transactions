package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.product.Grocery
import dynamo.account.acid.model.product.Medicine
import dynamo.account.acid.model.product.Product
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.ZoneOffset

class ProductConverter : Converter<Product> {
  private val groceryConverter = GroceryConverter()
  private val medicineConverter = MedicineConverter()
  override fun load(doc: Map<String, AttributeValue>): Product {
    return when (doc["type"]?.s()?.toString()?.let { Product.Type.valueOf(it) } ?: Product.Type.GROCERY) {
      Product.Type.GROCERY -> groceryConverter.load(doc)
      Product.Type.MEDICINE -> medicineConverter.load(doc)
    }
  }

  override fun convert(entity: Product): Map<String, AttributeValue> {
    val defaultValues = mapOf(
      "id" to AttributeValue.builder().n(entity.id.toString()).build(),
      "imageUrl" to AttributeValue.builder().s(entity.imageUrl).build(),
      "name" to AttributeValue.builder().s(entity.name).build(),
      "price" to AttributeValue.builder().n(entity.price.toString()).build(),
      "stock" to AttributeValue.builder().n(entity.stock.toString()).build(),
      "description" to AttributeValue.builder().s(entity.description).build(),
      "createdAt" to AttributeValue.builder().n(entity.createdAt.toEpochSecond(ZoneOffset.UTC).toString()).build()
    )

    return when (entity.type) {
      Product.Type.GROCERY -> defaultValues + groceryConverter.convert(entity as Grocery)
      Product.Type.MEDICINE -> defaultValues + medicineConverter.convert(entity as Medicine)
    }
  }
}