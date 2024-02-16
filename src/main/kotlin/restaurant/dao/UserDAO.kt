package restaurant.dao

import restaurant.entity.users.User

interface UserDAO {

    fun isAdmin(user: User) : Boolean

    fun getPasswordHash(password: String) : String
}