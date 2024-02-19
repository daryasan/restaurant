package restaurant.dao

import restaurant.DB.RestaurantSerializer
import restaurant.entity.Dish
import restaurant.entity.Menu
import restaurant.exceptions.DishException
import kotlin.time.Duration

class RuntimeMenuDAO : MenuDAO {

    val serializer = RestaurantSerializer()
    override fun addDish(dish: Dish) {
        var dishes = serializer.deserializeDishes()
        dishes.add(dish)
        serializer.serializeDishes(dishes)
    }

    override fun addDish(name: String, price: Int, currentAmount: Int, difficulty: Int) {
        val dish = Dish(name, price, currentAmount, difficulty)
        addDish(dish)
    }

    override fun findDishByName(name: String): Dish {
        val dishes = serializer.deserializeDishes()
        for (d in dishes) {
            if (d.name == name) {
                return d
            }
        }
        throw DishException("Such dish doesn't exist!")
    }

    override fun updateDish(dish: Dish) {
        var dishes = serializer.deserializeDishes()
        for (i in 0..<dishes.size) {
            if (dishes[i].name == dish.name) {
                dishes[i] = dish
                return
            }
        }
        throw DishException("Such dish doesn't exist!")
    }

    override fun getMenu(): Menu {
        val dishes = serializer.deserializeDishes()
        return Menu(dishes)
    }
}