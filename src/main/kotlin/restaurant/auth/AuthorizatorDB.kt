package restaurant.auth

import restaurant.DB.DataBaseDAO
import restaurant.dao.RuntimeUserDAO
import restaurant.dao.UserDAO
import restaurant.entity.users.AdminUser
import restaurant.entity.users.OrdinaryUser
import restaurant.entity.users.User
import restaurant.exceptions.EmptyFileException
import restaurant.exceptions.UserNotFoundException
import restaurant.exceptions.WrongAuthorizationException
import java.io.IOException

class AuthorizatorDB(val db: DataBaseDAO) : Authorizator {

    // creating new user
    override fun signUp(login: String, password: String, isAdmin: Boolean): User {
        try {
            if (db.userIsInDB(login)) {
                return signIn(login, password)
            }
        } finally {
            val userDAO: UserDAO = RuntimeUserDAO()
            if (isAdmin) {
                val user: AdminUser = AdminUser(
                    login,
                    userDAO.getPasswordHash(password)
                )
                db.addUser(user)
                return user
            } else {
                val user: OrdinaryUser = OrdinaryUser(
                    login,
                    userDAO.getPasswordHash(password)
                )
                db.addUser(user)
                return user
            }
        }

    }

    // logging in
    override fun signIn(login: String, password: String): User {
        val userDAO: UserDAO = RuntimeUserDAO()
        try {
            val user: User = db.searchUser(login)
            if (user.passwordHash == userDAO.getPasswordHash(password)) {
                return user
            } else if (user.passwordHash != userDAO.getPasswordHash(password)) {
                throw WrongAuthorizationException("Wrong password! Try again.")
            } else {
                throw WrongAuthorizationException("Something went wrong! Try again.")
            }
        } catch (e: UserNotFoundException) {
            throw WrongAuthorizationException("Wrong login! Try again.")
        } catch (e: EmptyFileException) {
            throw WrongAuthorizationException("Wrong login! Try again.")
        }

    }
}