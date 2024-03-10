package restaurant

import restaurant.dao.RuntimeOrderDAO
import restaurant.dao.RuntimeUserDAO
import restaurant.mutlithreading.OrderThread
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object Utils {

    private val orderDAO = RuntimeOrderDAO()
    private val userDAO = RuntimeUserDAO()
    private val revenuePath = "src/main/resources/revenue.txt"
    fun restartAllThreads() {
        val users = userDAO.getAllUsers()
        for (u in users) {
            if (orderDAO.userHasOrder(u)) {
                val orderThread = Thread(OrderThread(orderDAO.findOrderForUser(u)))
                orderThread.start()
            }
        }
    }

    fun getRevenue(): Int {
        return Files.readAllLines(Paths.get(revenuePath))[0].toString().toInt()
    }

    fun addToRevenue(value: Int) {
        File(revenuePath).printWriter().use { out ->
            out.println(value)
        }
    }

}