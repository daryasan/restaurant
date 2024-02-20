package restaurant.entity


import kotlinx.serialization.Serializable
import restaurant.ui.enums.OrderStatus

@Serializable
data class Order(
    var dishes: MutableList<Dish>,
    var status: OrderStatus,
    val user: User
) {

    fun cost(): Int {
        var sum = 0
        for (d in dishes) {
            sum += d.price
        }
        return sum
    }


    fun difficulty(): Int {
        return dishes.maxOf { it.difficultyTime }
    }


    override fun toString(): String {
        var s = "ORDER:\n"
        for (d in dishes) {
            s += "${d.toString()}\n"
        }
        s += "TOTAL: ${cost()}₽\n"
        s += "CURRENT STATUS: ${status.toString()}"
        return s
    }

}