package dynamo.account.acid.route.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.ktor.application.Application
import io.ktor.auth.Authentication.Feature.install
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt

class AuthModule(private val env: Configuration) {

  private val jwtIssuer = Key("jwt.domain", stringType)
  private val jwtAudience = Key("jwt.audience", stringType)
  private val jwtRealm = Key("jwt.realm", stringType)

  fun add(application: Application) {
    install(application) {
      jwt {
        realm = env[jwtRealm]
        verifier(makeJwtVerifier(env[jwtIssuer], env[jwtIssuer]))
        validate { credential ->
          if (credential.payload.audience.contains(env[jwtAudience])) JWTPrincipal(credential.payload) else null
        }
      }
    }
  }

  private val algorithm = Algorithm.HMAC256("secret")
  private fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
    .require(algorithm)
    .withAudience(audience)
    .withIssuer(issuer)
    .build()

}