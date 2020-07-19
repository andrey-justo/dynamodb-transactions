package dynamo.account.acid.actions.user

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.user.input.LoginCredentials
import dynamo.account.acid.actions.user.output.LoggedJWT
import dynamo.account.acid.model.user.signup.SignUp
import dynamo.account.acid.route.authentication.AuthModule

class JWTSignUp(private val authModule: AuthModule, private val signUp: SignUp) : Action<LoginCredentials, LoggedJWT> {
    override fun perform(input: LoginCredentials): LoggedJWT {
        val user = signUp.execute(input.username, input.password, input.type)

        val jwt = authModule.createJwt(userId = user.userId)
        return LoggedJWT(jwt)
    }
}