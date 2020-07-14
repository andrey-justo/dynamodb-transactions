package dynamo.account.acid.configuration.route

import dynamo.account.acid.route.TransactionRoute
import org.koin.dsl.module

object TransactionConfig {

  val transactionRouteConfig = module { single { TransactionRoute(get(), get()) } }

}