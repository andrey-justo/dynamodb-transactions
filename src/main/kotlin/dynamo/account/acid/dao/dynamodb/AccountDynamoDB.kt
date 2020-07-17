package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.dao.dynamodb.converter.AccountConverter
import dynamo.account.acid.model.account.Account
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.QueryRequest

class AccountDynamoDB(private val client: DynamoDbAsyncClient) : AccountDAO {
    private val accountTable = "Account"
    private val converter = AccountConverter()

    override fun create(account: Account) {
        client.putItem {
            it.tableName(accountTable)
                .item(converter.convert(account))
        }.get()
    }

    override fun get(id: String): Account {
        return client.getItem(
            GetItemRequest.builder().consistentRead(true)
                .tableName(accountTable)
                .key(mapOf("id" to AttributeValue.builder().s(id).build())).build()
        ).get().item().let { converter.load(it) }
    }

    override fun getByUserId(userId: Long): Account {
        return findByUserId(userId) ?: throw NoSuchElementException("No account found")
    }

    override fun findByUserId(userId: Long): Account? {
        val query = QueryRequest.builder()
            .tableName(accountTable)
            .indexName("account-user-index")
            .keyConditionExpression("#userId = :userId")
            .consistentRead(false)
            .expressionAttributeNames(mapOf("#userId" to "userId"))
            .expressionAttributeValues(mapOf(":userId" to AttributeValue.builder().s(userId.toString()).build()))
            .build()
        val item = client.query(query).get().items().firstOrNull()

        return item?.let { converter.load(it) }
    }
}