package restaurant.ui

import currentUser
import restaurant.dao.MenuDAO
import restaurant.dao.RuntimeDishDAO
import restaurant.dao.RuntimeMenuDAO
import restaurant.entity.Dish
import restaurant.ui.enums.AdminOptions
import restaurant.ui.enums.EnumChooser
import kotlin.system.exitProcess

class AdminUserStrategy : UserStrategy {

    val console: ConsoleOptionChooser = ConsoleOptionChooser()
    val checker: Checker = Checker()
    val enumChooser = EnumChooser()
    val menu: MenuDAO = RuntimeMenuDAO()
    val dishDAO = RuntimeDishDAO()
    override fun getMenu() {
        console.printSeparator()
        println("WHAT DO YOU WANT TO DO?")
        println("1.ADD NEW DISH TO MENU")
        println("2.EDIT DISH FROM MENU")
        println("3.LOG OUT")
        println("4.EXIT")
        console.printSeparator()

        when (enumChooser.checkAdminOption()) {
            AdminOptions.ADD_DISH -> {
                println("Enter dish name:")
                val name = checker.checkStringInput()
                println("Enter price of the dish:")
                val price = checker.checkIntInput()
                println("Enter the amount of dishes:")
                val amount = checker.checkIntInput()
                println("How long will it take to cook the ${name} in minutes?")
                println("Enter number of minutes:")
                val min = checker.checkIntInput()
                menu.addDish(name, price, amount, min)
                println("Successfully added!")
            }

            AdminOptions.EDIT_DISH -> {
                console.printSeparator()
                println("MENU:")
                val menuDishes = menu.getMenu().dishes
                for (i in 0..<menuDishes.size) {
                    println("${i + 1}.${menuDishes[i]}")
                }
                console.printSeparator()
                println("Enter number of a dish from menu:")
                val num = checker.checkRangeNumberInput(1, menuDishes.size)
                editDish(menuDishes[num - 1])
                println("Successfully edited!")
            }

            AdminOptions.LOG_OUT -> {
                currentUser = console.authorize()
                return
            }

            AdminOptions.EXIT -> {
                print("Exiting program...")
                exitProcess(0)
            }
        }
        console.printSeparator()
    }

    private fun editDish(dish: Dish) {
        println("1.PRESS 1 TO EDIT DISH PRICE")
        println("2.PRESS 2 TO EDIT DISH AMOUNT")
        println("3.PRESS 3 TO EDIT DISH DIFFICULTY")

        val opt = checker.checkRangeNumberInput(1, 4)
        when (opt) {

            1 -> {
                println("Enter new price of the dish:")
                println("Current price: ${dish.price}₽")
                val price = checker.checkIntInput()
                dishDAO.changePrice(dish, price)
            }

            2 -> {
                println("Enter new amount of dishes:")
                println("Current amount: ${dish.currentAmount}")
                val amount = checker.checkIntInput()
                dishDAO.changeAmount(dish, amount)
            }

            3 -> {
                println("How long will it take to cook ${dish.name} in minutes?")
                println("Current cooking time: ${dish.difficultyTime} minutes")
                println("Enter new number of minutes:")
                val min = checker.checkIntInput()
                dishDAO.changeDifficulty(dish, min)
            }
        }
    }

}