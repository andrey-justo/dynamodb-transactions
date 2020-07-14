package dynamo.account.acid.configuration

import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import dynamo.account.acid.App.Companion.config
import org.koin.dsl.module
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

class DynamoDBConfig {

  fun dynamoClient(credentialsProvider: AwsCredentialsProvider, region: String, endpoint: String): DynamoDbAsyncClient {
    return DynamoDbAsyncClient.builder()
      .credentialsProvider(credentialsProvider)
      .region(Region.of(region))
      .endpointOverride(URI.create(endpoint))
      .build()
  }

  companion object {
    private val instance = DynamoDBConfig()
    val dynamoConfig = module {
      val region = Key("aws.dynamodb.region", stringType)
      val endpoint = Key("aws.dynamodb.endpoint", stringType)
      single { instance.dynamoClient(get(), config[region], config[endpoint]) }
    }
  }

}