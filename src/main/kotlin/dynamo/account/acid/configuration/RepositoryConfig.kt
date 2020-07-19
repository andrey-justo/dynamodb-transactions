package dynamo.account.acid.configuration

import dynamo.account.acid.dao.*
import dynamo.account.acid.dao.dynamodb.*
import org.koin.dsl.module
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class RepositoryConfig {

  fun accountRepository(client: DynamoDbAsyncClient): AccountDAO = AccountDynamoDB(client)

  fun productRepository(client: DynamoDbAsyncClient): ProductDAO = ProductDynamoDB(client)

  fun transactionRepository(client: DynamoDbAsyncClient): TransactionDAO = TransactionDynamoDB(client)

  fun userRepository(client: DynamoDbAsyncClient): UserDAO = UserDynamoDB(client)

  fun credentialRepository(client: DynamoDbAsyncClient): CredentialDAO = CredentialDynamoDB(client)

  companion object {
    private val instance = RepositoryConfig()
    val repositoryConfig = module {
      single { instance.accountRepository(get()) }
      single { instance.productRepository(get()) }
      single { instance.transactionRepository(get()) }
      single { instance.userRepository(get()) }
      single { instance.credentialRepository(get()) }
    }
  }

}