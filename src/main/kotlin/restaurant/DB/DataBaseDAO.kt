package restaurant.DB

import restaurant.entity.User

interface DataBaseDAO {

    fun searchUser(login: String): User

    fun addUser(user: User)

    fun userIsInDB(login: String): Boolean

}