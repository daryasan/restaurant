import restaurant.entity.User
import restaurant.ui.ConsoleOptionChooser
import restaurant.ui.UserContext

val optionChooser: ConsoleOptionChooser = ConsoleOptionChooser()
var currentUser: User = optionChooser.authorize()

fun main(args: Array<String>) {

    try {
        while (true) {
            println("Current user now: ${currentUser.login}")
            optionChooser.getConsoleMenu(currentUser)
        }

    } catch (e: Exception) {
        println(e.message)
    }


}