package restaurant.ui

import restaurant.Utils
import restaurant.dao.MenuDAO
import restaurant.dao.RuntimeDishDAO
import restaurant.dao.RuntimeMenuDAO
import restaurant.entity.Dish
import restaurant.ui.enums.AdminOptions
import restaurant.ui.enums.EnumChooser
import kotlin.system.exitProcess
import currentUser as currentUser1

class AdminUserStrategy : UserStrategy {

    // some initializations
    private val console: ConsoleOptionChooser = ConsoleOptionChooser()
    private val checker: Checker = Checker()
    private val enumChooser = EnumChooser()
    private val menu: MenuDAO = RuntimeMenuDAO()
    private val dishDAO = RuntimeDishDAO()

    // strategy method
    override fun getMenu() {
        console.printSeparator()
        println("WHAT DO YOU WANT TO DO?")
        println("1.ADD NEW DISH TO MENU")
        println("2.EDIT DISH FROM MENU")
        println("3.SEE CURRENT REVENUE")
        println("4.LOG OUT")
        println("5.EXIT")
        console.printSeparator()

        // options
        when (enumChooser.checkAdminOption()) {
            AdminOptions.ADD_DISH -> {
                println("Enter dish name:")
                val name = checker.checkStringInput()
                println("Enter price of the dish:")
                val price = checker.checkIntInput()
                println("Enter the amount of dishes:")
                val amount = checker.checkIntInput()
                println("How long will it take to cook the $name in minutes?")
                println("Enter number of minutes:")
                val min = checker.checkIntInput()
                menu.addDish(name, price, amount, min)
                println("Successfully added!")
            }

            AdminOptions.EDIT_DISH -> {
                console.printSeparator()
                editDish(console.getItemFromMenuByConsole())
                println("Successfully edited!")
            }

            AdminOptions.SEE_REVENUE -> {
                println("Current revenue: ${Utils.getRevenue()}")
            }

            AdminOptions.LOG_OUT -> {
                currentUser1 = console.authorize()
                return
            }

            AdminOptions.EXIT -> {
                print("Exiting program...")
                exitProcess(0)
            }
        }
        console.printSeparator()
    }


    // additional menu to choose what to edit
    private fun editDish(dish: Dish) {
        println("1.PRESS 1 TO EDIT DISH PRICE")
        println("2.PRESS 2 TO EDIT DISH AMOUNT")
        println("3.PRESS 3 TO EDIT DISH DIFFICULTY")

        val opt = checker.checkRangeNumberInput(1, 3)
        // options
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