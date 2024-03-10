package restaurant.entity


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import restaurant.ui.enums.OrderStatus
import java.time.LocalDateTime

@Serializable
data class Order(
    var dishes: MutableList<Dish>,
    var status: OrderStatus,
    val user: User,
    @Contextual
    var finishTime: LocalDateTime
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
            s += "$d\n"
        }
        s += "TOTAL: ${cost()}₽\n"
        s += "CURRENT STATUS: $status"
        return s
    }

    companion object {
        fun getDifficulty(list: MutableList<Dish>): Int {
            return list.maxOf { it.difficultyTime }
        }
    }

}