package restaurant.entity.users

class AdminUser(
    login: String,
    passwordHash: String
) : User(login, passwordHash, true) {
}