package restaurant.ui

import restaurant.DB.SerializationDB
import restaurant.auth.AuthorizatorDB
import restaurant.entity.User
import restaurant.exceptions.WrongAuthorizationException

class ConsoleOptionChooser : OptionChooser {

    private val db: SerializationDB = SerializationDB()
    val checker: Checker = Checker()
    val auth: AuthorizatorDB = AuthorizatorDB(db)

    override fun authorize(): User {
        println("DO YOU HAVE AN ACCOUNT?")
        println("Print 1 if you want to sign in.")
        println("Print 2 if you want to sign up.")

        val opt = checker.signInOrSignUp()

        try {
            println("Enter login:")
            var login = checker.checkStringInput()
            println("Enter password:")
            val password = checker.checkStringInput()

            when (opt) {
                AuthOptions.SIGN_IN -> {
                    return auth.signIn(login, password)
                }

                AuthOptions.SIGN_UP -> {
                    println("Are you an administrator? Enter Y or N:")
                    val isAdmin = checker.checkYesOrNo()
                    return auth.signUp(login, password, isAdmin)
                }
            }
        } catch (e: WrongAuthorizationException) {
            println(e.message)
            return authorize()
        }
    }

}