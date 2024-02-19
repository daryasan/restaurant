package restaurant.DB

import restaurant.entity.User
import restaurant.exceptions.EmptyFileException
import restaurant.exceptions.UserNotFoundException

class SerializationDB : DataBaseDAO {

    val serializer: RestaurantSerializer = RestaurantSerializer()

    override fun searchUser(login: String): User {
        val users = serializer.deserializeUsers()
        for (u in users) {
            if (u.login == login) {
                return u
            }
        }
        throw UserNotFoundException("No such user!")
    }

    override fun addUser(user: User) {
        try {
            val users = serializer.deserializeUsers()
            users.add(user)
            serializer.serializeUsers(users)
        } catch (e: EmptyFileException) {
            val users = mutableListOf(user)
            serializer.serializeUsers(users)
        }

    }

    override fun userIsInDB(login: String): Boolean {
        val users = serializer.deserializeUsers()
        for (u in users) {
            if (u.login == login) {
                return true
            }
        }
        return false
    }
}