package restaurant.mutlithreading

import restaurant.dao.RuntimeOrderDAO
import restaurant.dao.RuntimeUserDAO
import restaurant.entity.Order
import restaurant.ui.VisitorUserStrategy
import restaurant.ui.enums.OrderStatus
import java.time.Duration
import java.time.LocalDateTime

class OrderThread(
    private val order: Order
) : Runnable {

    private val orderDAO = RuntimeOrderDAO()
    private val userDAO = RuntimeUserDAO()
    private val userStrategy = VisitorUserStrategy()

    override fun run() {
        // wait for 30 sec for an order to proceed
        if (order.status != OrderStatus.COOKING) {
            Thread.sleep(5000)
            order.status = OrderStatus.COOKING
            orderDAO.updateOrder(order)
        }

        // wait for order to cook
        try {
            Thread.sleep(Duration.between(LocalDateTime.now(), order.finishTime).toMillis())
        } catch (e: Exception) {
            order.status = OrderStatus.READY
            orderDAO.updateOrder(order)
            if (userDAO.isLoggedIn(order.user)) {
                userStrategy.checkOrder()
            }
        }


        order.status = OrderStatus.READY
        orderDAO.updateOrder(order)
        if (userDAO.isLoggedIn(order.user)) {
            userStrategy.checkOrder()
        }
    }
}