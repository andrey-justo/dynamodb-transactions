package dynamo.account.acid.route

import dynamo.account.acid.actions.account.AddAccount
import dynamo.account.acid.actions.account.GetAccount
import dynamo.account.acid.actions.account.GetAccountByUser
import dynamo.account.acid.actions.account.input.CreateAccountDTO
import dynamo.account.acid.actions.transactions.input.DepositDTO
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.routing

// TODO: separate more action into interface -> Impl
class AccountRoute(
  private val addAccount: AddAccount,
  private val getAccount: GetAccount,
  private val getAccountByUser: GetAccountByUser
) {

  fun Application.accountRouting() {
    routing {
      get("/api/v1/accounts") {
        val principal = call.authentication.principal as? JWTPrincipal
        principal?.payload?.subject?.let {
          val account = getAccount.perform(it)
          call.respond(account)
        } ?: call.respond(HttpStatusCode.Forbidden)
      }
      get("/api/v1/accounts/{userId}") {
        val userId = call.parameters["userId"]?.toLongOrNull()
        if (userId == null) {
          call.respond(HttpStatusCode.UnprocessableEntity)
        } else {
          val account = getAccountByUser.perform(userId)
          call.respond(account)
        }
      }
      put("api/v1/accounts") {
        val input: CreateAccountDTO = call.receive()
        val newAccount = addAccount.perform(input)
        call.respond(HttpStatusCode.Created, newAccount)
      }
    }
  }
}