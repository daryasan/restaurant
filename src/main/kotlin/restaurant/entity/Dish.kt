package restaurant.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Dish(
    val name: String,
    var price: Int,
    var currentAmount: Int,
    @Contextual
    var difficultyTime: Int
) {

    override fun toString(): String {
        return "${name.uppercase(Locale.getDefault())}: ${price}₽"
    }

}