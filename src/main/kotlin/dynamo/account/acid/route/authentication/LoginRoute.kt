package dynamo.account.acid.route.authentication

import dynamo.account.acid.actions.user.JWTLogin
import dynamo.account.acid.actions.user.input.LoginCredentials
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.loginRouting(
    jwtLogin: JWTLogin
) {
    route("login") {
        post {
            val input: LoginCredentials = call.receive()
            val credentials = jwtLogin.perform(input)
            call.respond(HttpStatusCode.OK, credentials)
        }
    }
}