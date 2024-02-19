package restaurant.dao

import restaurant.entity.Dish
import kotlin.time.Duration

class RuntimeDishDAO : DishDAO {

    val menu: MenuDAO = RuntimeMenuDAO()

    override fun changePrice(dish: Dish, newPrice: Int) {
        dish.price = newPrice
        menu.updateDish(dish)
    }

    override fun changeAmount(dish: Dish, newAmount: Int) {
        dish.currentAmount = newAmount
        menu.updateDish(dish)
    }

    override fun changeDifficulty(dish: Dish, newDifficulty: Int) {
        dish.difficultyTime = newDifficulty
        menu.updateDish(dish)
    }
}