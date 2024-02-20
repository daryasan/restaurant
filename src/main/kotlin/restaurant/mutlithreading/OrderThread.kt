package restaurant.mutlithreading

import restaurant.dao.RuntimeOrderDAO
import restaurant.dao.RuntimeUserDAO
import restaurant.entity.Order
import restaurant.ui.VisitorUserStrategy
import restaurant.ui.enums.OrderStatus

class OrderThread(
    private val order: Order
) : Runnable {

    private val orderDAO = RuntimeOrderDAO()
    private val userDAO = RuntimeUserDAO()
    private val userStrategy = VisitorUserStrategy()

    override fun run() {
        // wait for 30 sec for an order to proceed
        Thread.sleep(30000)
        order.status = OrderStatus.COOKING
        orderDAO.updateOrder(order)
        // wait for order to cook
        Thread.sleep(order.difficulty().toLong() * 60000)
        order.status = OrderStatus.READY
        orderDAO.updateOrder(order)
        if (userDAO.isLoggedIn(order.user)) {
            userStrategy.checkOrder()
        }
    }
}