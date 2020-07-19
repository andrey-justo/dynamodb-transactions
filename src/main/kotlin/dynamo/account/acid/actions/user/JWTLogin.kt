package dynamo.account.acid.actions.user

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.user.input.LoginCredentials
import dynamo.account.acid.actions.user.output.LoggedJWT
import dynamo.account.acid.model.user.login.Login
import dynamo.account.acid.route.authentication.AuthModule

class JWTLogin(private val authModule: AuthModule, private val login: Login) : Action<LoginCredentials, LoggedJWT> {
    override fun perform(input: LoginCredentials): LoggedJWT {
        val user = login.execute(input.username, input.password, input.type)

        val jwt = authModule.createJwt(userId = user.userId)
        return LoggedJWT(jwt)
    }
}