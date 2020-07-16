package dynamo.account.acid.configuration

import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import org.koin.dsl.module
import dynamo.account.acid.App.config
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain
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

    fun awsCredentials(accessKey: String, secretKey: String): AwsCredentialsProvider = AwsCredentialsProviderChain
            .builder()
            .addCredentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build()

    companion object {
        private val instance = DynamoDBConfig()
        val dynamoConfig = module {
            val region = Key("aws.dynamodb.region", stringType)
            val endpoint = Key("aws.dynamodb.endpoint", stringType)
            val secret = Key("aws.secret", stringType)
            val access = Key("aws.access", stringType)
            single { instance.dynamoClient(get(), config[region], config[endpoint]) }
            single { instance.awsCredentials(config[access], config[secret]) }
        }
    }

}