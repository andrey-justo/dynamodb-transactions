package dynamo.account.acid.route.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.natpryce.konfig.Configuration
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType

class AuthModule(private val env: Configuration) {

    val jwtIssuer = Key("jwt.domain", stringType)
    val jwtAudience = Key("jwt.audience", stringType)

    private val algorithm = Algorithm.HMAC256("secret")
    fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun createJwt(userId: String): String =
        JWT.create().withIssuer(env[jwtIssuer]).withAudience(env[jwtAudience]).withSubject(userId).sign(algorithm)

}