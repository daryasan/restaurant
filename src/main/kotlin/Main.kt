import restaurant.entity.User
import restaurant.ui.ConsoleOptionChooser


fun main(args: Array<String>) {

    val optionChooser: ConsoleOptionChooser = ConsoleOptionChooser()

    var currentUser: User = optionChooser.authorize()
    println("Current user now: ${currentUser.login}")


}