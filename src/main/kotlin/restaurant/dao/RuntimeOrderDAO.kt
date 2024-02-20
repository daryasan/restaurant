package restaurant.dao

import currentUser
import restaurant.DB.RestaurantSerializer
import restaurant.entity.Dish
import restaurant.entity.Order
import restaurant.exceptions.OrderException
import restaurant.mutlithreading.OrderThread
import restaurant.ui.enums.OrderStatus
import revenue

class RuntimeOrderDAO : OrderDAO {

    val serializer = RestaurantSerializer()
    override fun addDishToOrder(order: Order, dish: Dish, amount: Int): Order {
        for (i in 1..amount)
            order.dishes.add(dish)
        return order
    }

    override fun initOrder(): Order {
        val list: MutableList<Dish> = mutableListOf()
        return Order(list, OrderStatus.PROCESSING, currentUser)
    }

    override fun makeOrder(order: Order): OrderThread {
        if (userHasOrder()) {
            val orders = serializer.deserializeOrders()
            for (o in orders) {
                if (o.user.login == currentUser.login) {
                    o.dishes.addAll(order.dishes)
                    serializer.serializeOrders(orders)
                    return OrderThread(order)
                }
            }
        }
        val orders = serializer.deserializeOrders()
        order.status = OrderStatus.ADDED
        orders.add(order)
        serializer.serializeOrders(orders)
        return OrderThread(order)
    }

    override fun userHasOrder(): Boolean {
        val orders = serializer.deserializeOrders()
        for (o in orders) {
            if (o.user.login == currentUser.login) {
                return true
            }
        }
        return false
    }

    override fun findOrderForCurrentUser(): Order {
        val orders = serializer.deserializeOrders()
        for (o in orders) {
            if (o.user.login == currentUser.login) {
                return o
            }
        }
        throw OrderException("User ${currentUser.login} doesn't have any orders yet!")

    }

    override fun deleteCurrentUserOrder() {
        val orders = serializer.deserializeOrders()
        for (o in orders) {
            if (o.user.login == currentUser.login) {
                orders.remove(o)
                serializer.serializeOrders(orders)
                return
            }
        }
        throw OrderException("User ${currentUser.login} doesn't have any orders yet!")
    }

    override fun updateOrder(order: Order) {
        val orders = serializer.deserializeOrders()
        for (i in 0..<orders.size) {
            if (orders[i].user.login == currentUser.login) {
                orders[i] = order
            }
        }
        serializer.serializeOrders(orders)
    }

    override fun pay(order: Order) {
        revenue += order.cost()
        deleteCurrentUserOrder()
    }
}