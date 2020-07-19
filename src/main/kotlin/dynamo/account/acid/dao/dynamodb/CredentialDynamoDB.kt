package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.CredentialDAO
import dynamo.account.acid.dao.UserDAO
import dynamo.account.acid.dao.dynamodb.converter.CredentialConverter
import dynamo.account.acid.dao.dynamodb.converter.ProductConverter
import dynamo.account.acid.dao.dynamodb.converter.UserConverter
import dynamo.account.acid.dao.dynamodb.credential.CredentialDTO
import dynamo.account.acid.model.product.Product
import dynamo.account.acid.model.user.Credential
import dynamo.account.acid.model.user.CredentialType
import dynamo.account.acid.model.user.User
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import java.lang.IllegalStateException
import java.util.concurrent.ExecutionException

class CredentialDynamoDB(private val client: DynamoDbAsyncClient) : CredentialDAO {
    private val credentialTable = "Credential"
    private val converter = CredentialConverter()

    override fun check(principal: String, type: CredentialType, accessToken: String): Credential {
        val credential: CredentialDTO = client.getItem(
            GetItemRequest.builder().consistentRead(true)
                .tableName(credentialTable)
                .key(mapOf("principal" to AttributeValue.builder().s(principal).build())).build()
        ).get().item().let {
            CredentialDTO(
                userId = it["id"]!!.s().toString(),
                type = CredentialType.valueOf(it["type"]!!.s().toString()),
                principal = it["principal"]!!.s().toString(),
                secret = it["secret"]!!.s().toString()
            )
        }

        if (credential.secret != accessToken) {
            throw IllegalStateException("Secret not allowed")
        }

        return Credential(principal = credential.principal, type = credential.type, userId = credential.userId)
    }

    override fun add(credential: Credential, accessToken: String) {
        client.putItem {
            val attributes = converter.convert(credential)
            val secret = mapOf("secret" to AttributeValue.builder().s(accessToken).build())
            it.tableName(credentialTable)
                .item(attributes + secret)
        }.get()
    }

    override fun find(principal: String): Credential? {
        return try {
            client.getItem(
                GetItemRequest.builder().consistentRead(true)
                    .tableName(credentialTable)
                    .key(mapOf("principal" to AttributeValue.builder().s(principal).build())).build()
            ).get().item().let { converter.load(it) }
        } catch (e: ExecutionException) {
            if (e.cause is DynamoDbException) null else throw e
        }
    }
}