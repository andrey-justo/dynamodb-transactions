package dynamo.account.acid.configuration

import dynamo.account.acid.actions.user.JWTLogin
import dynamo.account.acid.actions.user.JWTSignUp
import dynamo.account.acid.model.user.CredentialType
import dynamo.account.acid.model.user.login.EmailLoginDelegate
import dynamo.account.acid.model.user.login.Login
import dynamo.account.acid.model.user.login.LoginDelegate
import dynamo.account.acid.model.user.signup.EmailValidateCredentials
import dynamo.account.acid.model.user.signup.SignUp
import dynamo.account.acid.model.user.signup.ValidateCredentials
import dynamo.account.acid.route.authentication.AuthModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

class UserActionConfig {

    fun jwtLogin(authModule: AuthModule, login: Login) = JWTLogin(authModule, login)

    fun jwtSignUp(authModule: AuthModule, signUp: SignUp) = JWTSignUp(authModule, signUp)

    companion object {
        private val instance = UserActionConfig()
        val actionConfig = module {
            single { instance.jwtLogin(get(), get()) }
            single { instance.jwtSignUp(get(), get()) }
            // Delegates
            single<LoginDelegate>(named("EmailLogin")) { EmailLoginDelegate(get()) }
            single<ValidateCredentials>(named("EmailSign")) { EmailValidateCredentials() }
            // Services
            single {
                SignUp(
                    credentialDAO = get(),
                    userDAO = get(),
                    delegates = mapOf(CredentialType.EMAIL to get(named("EmailSign")))
                )
            }
            single { Login(userDAO = get(), delegates = mapOf(CredentialType.EMAIL to get(named("EmailLogin")))) }
        }
    }

}