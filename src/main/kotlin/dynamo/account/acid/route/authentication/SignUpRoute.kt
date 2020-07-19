package dynamo.account.acid.route.authentication

import dynamo.account.acid.actions.user.JWTSignUp
import dynamo.account.acid.actions.user.input.LoginCredentials
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.signUpRouting(
    jwtSignUp: JWTSignUp
) {
    route("signUp") {
        post {
            val input: LoginCredentials = call.receive()
            val credentials = jwtSignUp.perform(input)
            call.respond(HttpStatusCode.Created, credentials)
        }
    }
}