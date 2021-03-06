package dynamo.account.acid.model.user

import java.time.LocalDateTime

data class User(
    val userId: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isActive: Boolean = true,
    val credentials: List<Credential>
)