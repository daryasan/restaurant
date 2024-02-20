package restaurant.dao

import restaurant.entity.Dish
import restaurant.entity.Order
import restaurant.mutlithreading.OrderThread

interface OrderDAO {

    // add dish to existing order
    fun addDishToOrder(order: Order, dish: Dish, amount: Int): Order

    fun initOrder(): Order

    fun makeOrder(order: Order): OrderThread

    fun userHasOrder(): Boolean

    fun findOrderForCurrentUser(): Order

    fun deleteCurrentUserOrder()

    fun updateOrder(order: Order)

    fun pay(order: Order)

}