package dynamo.account.acid.route

import dynamo.account.acid.actions.product.AddGrocery
import dynamo.account.acid.actions.product.AddMedicine
import dynamo.account.acid.actions.product.GetProduct
import dynamo.account.acid.actions.transactions.input.WithdrawDTO
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
class ProductRoute(
  private val addGrocery: AddGrocery,
  private val addMedicine: AddMedicine,
  private val getProduct: GetProduct
) {

  fun Application.accountRouting() {
    routing {
      get("/api/v1/products/{productId}") {
        val product = getProduct.perform(call.parameters["productId"]!!.toLong())
        call.respond(product)
      }
      put("/api/v1/products") {
        val principal = call.authentication.principal as? JWTPrincipal
        principal?.payload?.subject?.let {

        } ?: call.respond(HttpStatusCode.Forbidden)
      }
    }
  }
}