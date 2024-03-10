package restaurant.dao

import currentUser
import restaurant.DB.RestaurantSerializer
import restaurant.Utils
import restaurant.entity.Dish
import restaurant.entity.Order
import restaurant.entity.User
import restaurant.exceptions.OrderException
import restaurant.mutlithreading.OrderThread
import restaurant.ui.enums.OrderStatus
import java.time.LocalDateTime

class RuntimeOrderDAO : OrderDAO {

    val serializer = RestaurantSerializer()
    override fun addDishToOrder(order: Order, dish: Dish, amount: Int): Order {
        for (i in 1..amount)
            order.dishes.add(dish)
        return order
    }

    override fun initOrder(): Order {
        val list: MutableList<Dish> = mutableListOf()
        return Order(
            list,
            OrderStatus.PROCESSING,
            currentUser,
            LocalDateTime.now()
        )
    }

    override fun makeOrder(order: Order): OrderThread {
        if (userHasOrder()) {
            val orders = serializer.deserializeOrders()
            for (o in orders) {
                if (o.user.login == currentUser.login) {
                    o.dishes.addAll(order.dishes)
                    order.finishTime = order.finishTime.plusMinutes(order.difficulty().toLong())
                    serializer.serializeOrders(orders)
                    return OrderThread(order)
                }
            }
        }
        val orders = serializer.deserializeOrders()
        order.status = OrderStatus.ADDED
        order.finishTime.plusMinutes(order.difficulty().toLong())
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

    override fun userHasOrder(user: User): Boolean {
        val orders = serializer.deserializeOrders()
        for (o in orders) {
            if (o.user.login == user.login) {
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

    override fun findOrderForUser(user: User): Order {
        val orders = serializer.deserializeOrders()
        for (o in orders) {
            if (o.user.login == user.login) {
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
        Utils.addToRevenue(order.cost())
        deleteCurrentUserOrder()
    }
}