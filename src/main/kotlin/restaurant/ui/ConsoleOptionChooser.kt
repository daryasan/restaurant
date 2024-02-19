package restaurant.ui

import restaurant.DB.SerializationDB
import restaurant.auth.Authorizator
import restaurant.auth.AuthorizatorDB
import restaurant.entity.User
import restaurant.exceptions.WrongAuthorizationException
import restaurant.ui.enums.AuthOptions
import restaurant.ui.enums.EnumChooser

class ConsoleOptionChooser : OptionChooser {

    private val db: SerializationDB = SerializationDB()
    val checker: Checker = Checker()
    val auth: Authorizator = AuthorizatorDB(db)
    val enumChooser = EnumChooser()
    var context: UserContext = UserContext(VisitorUserStrategy())

    override fun authorize(): User {
        printSeparator()
        println("DO YOU HAVE AN ACCOUNT?")
        println("Print 1 if you want to sign in.")
        println("Print 2 if you want to sign up.")
        printSeparator()

        val opt = enumChooser.signInOrSignUp()

        try {
            println("Enter login:")
            var login = checker.checkStringInput()
            println("Enter password:")
            val password = checker.checkStringInput()

            when (opt) {
                AuthOptions.SIGN_IN -> {
                    printSeparator()
                    return auth.signIn(login, password)
                }

                AuthOptions.SIGN_UP -> {
                    println("Are you an administrator? Enter Y or N:")
                    val isAdmin = checker.checkYesOrNo()
                    printSeparator()
                    return auth.signUp(login, password, isAdmin)
                }
            }
        } catch (e: WrongAuthorizationException) {
            println(e.message)
            return authorize()
        }
    }


    override fun getConsoleMenu(user: User) {
        if (user.isAdmin) {
            context = UserContext(AdminUserStrategy())
        } else {
            context = UserContext(VisitorUserStrategy())
        }
        context.showMenu()
    }

    fun printSeparator() {
        println("---------------------------------")
    }


}