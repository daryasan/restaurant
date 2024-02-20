package restaurant.ui.enums

import restaurant.exceptions.UIException
import restaurant.ui.Checker

class EnumChooser {

    val checker = Checker()

    // check options
    fun signInOrSignUp(): AuthOptions {

        val option: Int = checker.checkRangeNumberInput(1, 2)
        when (option) {
            1 -> return AuthOptions.SIGN_IN
            2 -> return AuthOptions.SIGN_UP
        }
        throw UIException("Wrong input!")
    }

    fun checkAdminOption(): AdminOptions {
        val num = checker.checkRangeNumberInput(1, 5)
        when (num) {
            1 -> return AdminOptions.ADD_DISH
            2 -> return AdminOptions.EDIT_DISH
            3 -> return AdminOptions.SEE_REVENUE
            4 -> return AdminOptions.LOG_OUT
            5 -> return AdminOptions.EXIT
        }
        throw UIException("Wrong admin option!")
    }

    fun checkVisitorOptions(): VisitorOptions {
        val num = checker.checkRangeNumberInput(1, 5)
        when (num) {
            1 -> return VisitorOptions.MAKE_ORDER
            2 -> return VisitorOptions.SEE_ORDER
            3 -> return VisitorOptions.CANCEL_ORDER
            4 -> return VisitorOptions.LOG_OUT
            5 -> return VisitorOptions.EXIT
        }
        throw UIException("Wrong visitor option!")
    }
}