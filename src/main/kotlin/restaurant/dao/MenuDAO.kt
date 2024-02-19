package restaurant.dao

import restaurant.entity.Dish
import restaurant.entity.Menu
import kotlin.time.Duration

interface MenuDAO {

    fun addDish(dish: Dish)

    fun addDish(
        name: String,
        price: Int,
        currentAmount: Int,
        difficulty: Int
    )

    fun findDishByName(name: String): Dish

    // updates existing dish with some new parameters in menu
    fun updateDish(dish: Dish)

    fun getMenu() : Menu

}