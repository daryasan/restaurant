import restaurant.Utils
import restaurant.entity.User
import restaurant.ui.ConsoleOptionChooser

val optionChooser: ConsoleOptionChooser = ConsoleOptionChooser()
var currentUser: User = optionChooser.authorize()

fun main(args: Array<String>) {

    // starting all processing orders
    Utils.restartAllThreads()

    try {
        while (true) {
            println("Current user now: ${currentUser.login}")
            optionChooser.getConsoleMenu(currentUser)
        }

    } catch (e: Exception) {
        println(e.message)
    }


}