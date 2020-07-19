package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.UserDAO
import dynamo.account.acid.dao.dynamodb.converter.ProductConverter
import dynamo.account.acid.dao.dynamodb.converter.UserConverter
import dynamo.account.acid.model.product.Product
import dynamo.account.acid.model.user.User
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest

class UserDynamoDB(private val client: DynamoDbAsyncClient) : UserDAO {
    private val userTable = "User"
    private val converter = UserConverter()

    override fun add(user: User) {
        client.putItem {
            it.tableName(userTable)
                .item(converter.convert(user))
        }.get()
    }

    override fun update(user: User) {
        client.updateItem() {
            it.tableName(userTable)
                .key(mapOf("userId" to AttributeValue.builder().s(user.userId).build()))
                .attributeUpdates(
                    converter.convert(user)
                        .mapValues {
                            AttributeValueUpdate.builder().value(it.value).build()
                        })
        }.get()
    }

    override fun get(id: String): User {
        return client.getItem(
            GetItemRequest.builder().consistentRead(true)
                .tableName(userTable)
                .key(mapOf("userId" to AttributeValue.builder().s(id).build())).build()
        ).get().item().let { converter.load(it) }
    }
}