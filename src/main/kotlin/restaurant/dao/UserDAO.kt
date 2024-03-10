package restaurant.dao

import restaurant.entity.User

interface UserDAO {

    fun isAdmin(user: User): Boolean
    fun getPasswordHash(password: String): String
    fun isLoggedIn(user: User): Boolean
    fun getAllUsers() : List<User>
}