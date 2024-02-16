package restaurant.auth

import restaurant.entity.users.User

interface Authorizator {

    fun signUp(login : String, password: String, isAdmin: Boolean) : User

    fun signIn(login : String, password: String) : User

}