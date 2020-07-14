package dynamo.account.acid.configuration

import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.dao.TransactionDAO
import dynamo.account.acid.dao.dynamodb.AccountDynamoDB
import dynamo.account.acid.dao.dynamodb.ProductDynamoDB
import dynamo.account.acid.dao.dynamodb.TransactionDynamoDB
import org.koin.dsl.module
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class RepositoryConfig {

  fun accountRepository(client: DynamoDbAsyncClient): AccountDAO = AccountDynamoDB(client)

  fun productRepository(client: DynamoDbAsyncClient): ProductDAO = ProductDynamoDB(client)

  fun transactionRepository(client: DynamoDbAsyncClient): TransactionDAO = TransactionDynamoDB(client)

  companion object {
    private val instance = RepositoryConfig()
    val repositoryConfig = module {
      single { instance.accountRepository(get()) }
      single { instance.productRepository(get()) }
      single { instance.transactionRepository(get()) }
    }
  }

}