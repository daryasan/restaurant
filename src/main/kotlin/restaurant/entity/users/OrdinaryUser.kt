package restaurant.entity.users

import kotlinx.serialization.Serializable

@Serializable
class OrdinaryUser(
    login: String,
    passwordHash: String
) : User(login, passwordHash, false) {
}