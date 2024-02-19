package restaurant.dao

import restaurant.entity.Dish
import kotlin.time.Duration

interface DishDAO {

    fun changePrice(dish: Dish, newPrice: Int)

    fun changeAmount(dish: Dish, newAmount: Int)

    fun changeDifficulty(dish: Dish, newDifficulty: Int)

}