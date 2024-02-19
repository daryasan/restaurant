package restaurant.entity

import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    var dishes: MutableList<Dish>
)