package dynamo.account.acid.configuration.route

import dynamo.account.acid.route.ProductRoute
import dynamo.account.acid.route.TransactionRoute
import org.koin.dsl.module

object ProductConfig {

  val productRouteConfig = module { single { ProductRoute(get(), get(), get()) } }

}