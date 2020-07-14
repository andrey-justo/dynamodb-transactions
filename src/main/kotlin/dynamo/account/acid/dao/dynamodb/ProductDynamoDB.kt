package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.dao.dynamodb.converter.ProductConverter
import dynamo.account.acid.model.product.Product
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest

class ProductDynamoDB(private val client: DynamoDbAsyncClient): ProductDAO {
  private val converter = ProductConverter()
  override fun add(product: Product) {
    client.putItem {
      converter.convert(product)
    }.get()
  }

  override fun get(id: Long): Product {
    return client.getItem(
      GetItemRequest.builder().consistentRead(true)
        .key(mapOf("id" to AttributeValue.builder().s(id.toString()).build())).build()
    ).get().item().let { converter.load(it) }
  }
}