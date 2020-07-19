/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package dynamo.account.acid

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import dynamo.account.acid.App.config
import dynamo.account.acid.configuration.AccountActionConfig
import dynamo.account.acid.configuration.DynamoDBConfig.Companion.dynamoConfig
import dynamo.account.acid.configuration.ProductActionConfig
import dynamo.account.acid.configuration.RepositoryConfig.Companion.repositoryConfig
import dynamo.account.acid.configuration.TransactionActionConfig
import dynamo.account.acid.configuration.UserActionConfig
import dynamo.account.acid.route.accountRouting
import dynamo.account.acid.route.authentication.AuthModule
import dynamo.account.acid.route.authentication.loginRouting
import dynamo.account.acid.route.authentication.signUpRouting
import dynamo.account.acid.route.productRouting
import dynamo.account.acid.route.transactionRouting
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin
import org.koin.dsl.module

object App {
    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("defaults.properties")
}

fun main(args: Array<String>) {
    val authModule = module {
        single { AuthModule(config) }
    }
    val koinApp = startKoin {
        // use Koin logger
        printLogger()
        // declare modules
        modules(
            // repository and cloud provider config
            dynamoConfig,
            repositoryConfig,
            // actions and services configuration
            authModule,
            TransactionActionConfig.actionConfig,
            AccountActionConfig.actionConfig,
            ProductActionConfig.actionConfig,
            UserActionConfig.actionConfig
        )
    }

    val authentication: AuthModule = koinApp.koin.get()
    val serverPort = Key("SERVER_PORT", intType)
    val server = embeddedServer(Netty, port = App.config.getOrElse(serverPort, 8080)) {
        authentication.add(this)
        install(DefaultHeaders)
        install(Compression)
        install(ContentNegotiation) {
            gson {}
        }
        install(Routing) {
            route("api/v1") {
                accountRouting(koinApp.koin.get(), koinApp.koin.get(), koinApp.koin.get())
                transactionRouting(koinApp.koin.get(), koinApp.koin.get())
                productRouting(koinApp.koin.get(), koinApp.koin.get(), koinApp.koin.get())
                loginRouting(koinApp.koin.get())
                signUpRouting(koinApp.koin.get())
            }
        }
    }
    server.start(wait = true)
}