package restaurant.DB

import restaurant.entity.users.User
import restaurant.exceptions.EmptyFileException
import restaurant.exceptions.UserNotFoundException
import java.io.IOException

class SerializationDB : DataBaseDAO {

    val serializer: RestaurantSerializer = RestaurantSerializer()

    override fun searchUser(login: String): User {
        val users = serializer.deserialize<User>(serializer.userPath)
        for (u in users) {
            if (u.login == login) {
                return u
            }
        }
        throw UserNotFoundException("No such user!")
    }

    override fun addUser(user: User) {
        try {
            val users = serializer.deserialize<User>(serializer.userPath)
            users.add(user)
            serializer.serialize(users)
        } catch (e: EmptyFileException) {
            val users = mutableListOf(user)
            serializer.serialize(users)
        }

    }

    override fun userIsInDB(login: String): Boolean {
        val users = serializer.deserialize<User>(serializer.userPath)
        for (u in users) {
            if (u.login == login) {
                return true
            }
        }
        return false
    }
}