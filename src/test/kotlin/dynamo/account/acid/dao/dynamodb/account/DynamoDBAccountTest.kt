package dynamo.account.acid.dao.dynamodb.account

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import dynamo.account.acid.dao.dynamodb.AccountDynamoDB
import dynamo.account.acid.model.account.Account
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.mockito.Mockito.`when`
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class DynamoDBAccountTest {

    lateinit var client: DynamoDbAsyncClient
    lateinit var dao: AccountDynamoDB

    @BeforeEach
    fun setup() {
        client = mock()
        dao = AccountDynamoDB(client)
    }

    private fun validAccountMap() = mapOf(
        "id" to AttributeValue.builder().s("12345").build(),
        "balance" to AttributeValue.builder().n("10").build(),
        "userId" to AttributeValue.builder().n("1234").build(),
        "createdAt" to AttributeValue.builder()
            .n(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()).build(),
        "limits" to AttributeValue.builder().n("100").build()
    )

    @Test
    fun `get account by userId`() {
        val response = QueryResponse.builder().items(
            validAccountMap()
        ).build()
        `when`(client.query(any<QueryRequest>())).thenReturn(
            CompletableFuture.completedFuture(
                response
            )
        )

        val account = dao.findByUserId(1234)
        Assertions.assertAll(
            Executable { Assertions.assertNotNull(account) },
            Executable { Assertions.assertEquals(BigDecimal.TEN, account?.balance) },
            Executable { Assertions.assertEquals(BigDecimal(100), account?.limits) },
            Executable { Assertions.assertEquals("12345", account?.id) },
            Executable { Assertions.assertEquals(1234L, account?.userId) }
        )
    }

    @Test
    fun `get account by id`() {
        val response = GetItemResponse.builder().item(validAccountMap()).build()
        `when`(client.getItem(any<GetItemRequest>())).thenReturn(
            CompletableFuture.completedFuture(
                response
            )
        )

        val account = dao.get("12345")
        Assertions.assertAll(
            Executable { Assertions.assertEquals(BigDecimal.TEN, account.balance) },
            Executable { Assertions.assertEquals(BigDecimal(100), account.limits) },
            Executable { Assertions.assertEquals("12345", account.id) },
            Executable { Assertions.assertEquals(1234L, account.userId) }
        )
    }

    @Test
    fun `create account`() {
        val memAccount = Account(id = "12345", balance = BigDecimal.TEN, limits = BigDecimal(100), userId = 1234)
        val response = PutItemResponse.builder().build()
        `when`(client.putItem(any<Consumer<PutItemRequest.Builder>>())).thenReturn(
            CompletableFuture.completedFuture(
                response
            )
        )

        Assertions.assertDoesNotThrow() { dao.create(memAccount) }
    }
}