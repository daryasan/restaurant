package restaurant.dao

import currentUser
import restaurant.entity.User
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class RuntimeUserDAO : UserDAO {
    override fun isAdmin(user: User): Boolean {
        return user.isAdmin
    }

    override fun getPasswordHash(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        val hashInBytes = md.digest(password.toByteArray(StandardCharsets.UTF_8))
        val sb = StringBuilder()
        for (b in hashInBytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    override fun isLoggedIn(user: User): Boolean {
        return user.login == currentUser.login
    }

}