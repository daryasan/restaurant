package restaurant.ui

import currentUser
import restaurant.DB.SerializationDB
import restaurant.auth.Authorizator
import restaurant.auth.AuthorizatorDB
import restaurant.dao.MenuDAO
import restaurant.dao.RuntimeMenuDAO
import restaurant.entity.Dish
import restaurant.entity.User
import restaurant.exceptions.WrongAuthorizationException
import restaurant.ui.enums.AuthOptions
import restaurant.ui.enums.EnumChooser

class ConsoleOptionChooser : OptionChooser {

    private val db: SerializationDB = SerializationDB()
    private val checker: Checker = Checker()
    private val auth: Authorizator = AuthorizatorDB(db)
    private val enumChooser = EnumChooser()
    private val menu: MenuDAO = RuntimeMenuDAO()


    override fun authorize(): User {

        currentUser = User("notAuthorized", "null", false)

        printSeparator()
        println("DO YOU HAVE AN ACCOUNT?")
        println("Print 1 if you want to sign in.")
        println("Print 2 if you want to sign up.")
        printSeparator()

        val opt = enumChooser.signInOrSignUp()

        try {
            println("Enter login:")
            val login = checker.checkStringInput()
            println("Enter password:")
            val password = checker.checkStringInput()

            return when (opt) {
                AuthOptions.SIGN_IN -> {
                    printSeparator()
                    auth.signIn(login, password)
                }

                AuthOptions.SIGN_UP -> {
                    println("Are you an administrator?")
                    val isAdmin = checker.checkYesOrNo()
                    printSeparator()
                    auth.signUp(login, password, isAdmin)
                }
            }
        } catch (e: WrongAuthorizationException) {
            println(e.message)
            return authorize()
        }
    }


    override fun getConsoleMenu(user: User) {
        val context: UserContext = if (user.isAdmin) {
            UserContext(AdminUserStrategy())
        } else {
            UserContext(VisitorUserStrategy())
        }
        context.showMenu()
    }

    fun printSeparator() {
        println("---------------------------------")
    }

    private fun printMenu() {
        printSeparator()
        println("MENU:")
        val menuDishes = menu.getMenu().dishes
        for (i in 0..<menuDishes.size) {
            println("${i + 1}.${menuDishes[i]}")
        }
        printSeparator()
    }

    fun getItemFromMenuByConsole(): Dish {
        printMenu()
        val menuDishes = menu.getMenu().dishes
        println("Enter number of a dish from menu to choose it:")
        val num = checker.checkRangeNumberInput(1, menuDishes.size)
        return menuDishes[num - 1]
    }


}