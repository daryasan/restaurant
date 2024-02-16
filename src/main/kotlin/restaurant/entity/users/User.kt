package restaurant.entity.users

import kotlinx.serialization.Serializable

@Serializable
abstract class User(
    val login: String,
    val passwordHash: String,
    val isAdmin: Boolean
)