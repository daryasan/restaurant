package restaurant.auth

import restaurant.DB.DataBaseDAO
import restaurant.dao.RuntimeUserDAO
import restaurant.dao.UserDAO
import restaurant.entity.User
import restaurant.exceptions.EmptyFileException
import restaurant.exceptions.UserNotFoundException
import restaurant.exceptions.WrongAuthorizationException

class AuthorizatorDB(val db: DataBaseDAO) : Authorizator {

    // creating new user
    override fun signUp(login: String, password: String, isAdmin: Boolean): User {

        if (db.userIsInDB(login)) {
            throw WrongAuthorizationException("Seems like you are already registered! Sing in, please!")
        }
        val userDAO: UserDAO = RuntimeUserDAO()
        return if (isAdmin) {
            val user = User(
                login,
                userDAO.getPasswordHash(password),
                true
            )
            db.addUser(user)
            user
        } else {
            val user = User(
                login,
                userDAO.getPasswordHash(password),
                false
            )
            db.addUser(user)
            user
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