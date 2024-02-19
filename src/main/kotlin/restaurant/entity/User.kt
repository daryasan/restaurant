package restaurant.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val passwordHash: String,
    val isAdmin: Boolean
)