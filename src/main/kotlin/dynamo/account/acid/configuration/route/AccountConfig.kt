package dynamo.account.acid.configuration.route

import dynamo.account.acid.route.AccountRoute
import org.koin.dsl.module

object AccountConfig {

  val accountRouteConfig = module { single { AccountRoute(get(), get(), get()) } }

}