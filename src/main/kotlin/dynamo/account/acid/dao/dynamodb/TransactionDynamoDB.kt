package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.TransactionDAO
import dynamo.account.acid.dao.dynamodb.converter.TransactionConverter
import dynamo.account.acid.model.account.Transaction
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class TransactionDynamoDB(private val client: DynamoDbAsyncClient) : TransactionDAO {
  private val converter = TransactionConverter()

  override fun add(transaction: Transaction) {
    client.putItem {
      converter.convert(transaction)
    }.get()
  }
}