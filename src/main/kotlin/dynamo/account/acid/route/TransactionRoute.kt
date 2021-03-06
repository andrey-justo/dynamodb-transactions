package dynamo.account.acid.route

import dynamo.account.acid.actions.transactions.CreateDeposit
import dynamo.account.acid.actions.transactions.WithdrawMoney
import dynamo.account.acid.actions.transactions.input.DepositDTO
import dynamo.account.acid.actions.transactions.input.WithdrawDTO
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.routing.routing

// TODO: separate more action into interface -> Impl
fun Route.transactionRouting(
    createDeposit: CreateDeposit,
    withdrawMoney: WithdrawMoney
) {
    authenticate {
        route("transactions") {
            put("deposit") {
                val principal = call.authentication.principal as JWTPrincipal
                val operation = call.receive<TransactionOperation>()
                principal.payload.subject?.let {
                    val transaction = createDeposit.perform(DepositDTO(amount = operation.amount, accountId = it))
                    call.respond(transaction)
                }
            }
            put("withdraw") {
                val principal = call.authentication.principal as JWTPrincipal
                val operation = call.receive<TransactionOperation>()
                principal.payload.subject?.let {
                    val transaction = withdrawMoney.perform(WithdrawDTO(amount = operation.amount, accountId = it))
                    call.respond(transaction)
                }
            }
        }
    }
}