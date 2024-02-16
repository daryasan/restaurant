package restaurant.ui

import restaurant.exceptions.UIException

class Checker {

    fun signInOrSignUp(): AuthOptions {

        val option: Int = checkRangeNumberInput(1, 2)
        when (option) {
            1 -> return AuthOptions.SIGN_IN
            2 -> return AuthOptions.SIGN_UP
        }
        throw UIException("Wrong input!")
    }

    fun checkStringInput(
        errorMessage: String =
            "Nothing entered. Please try again:"
    ): String {
        var line = readlnOrNull()
        while (line == null) {
            println(errorMessage)
            line = readlnOrNull()
        }
        return line
    }

    fun checkYesOrNo(
        errorMessage: String =
            "Sorry, wrong letter entered! Try again."
    ): Boolean {
        var line = readlnOrNull()
        while (line == null || (line != "Y" && line != "N")) {
            println(errorMessage)
            line = readlnOrNull()
        }
        when (line) {
            "Y" -> return true
            "N" -> return false
        }
        throw UIException(errorMessage)
    }

    fun checkRangeNumberInput(
        leftBorder: Int,
        rightBorder: Int,
        errorMessage: String =
            "Sorry, wrong number entered! Try again."
    ): Int {
        var line = readlnOrNull()
        while (line == null) {
            println(errorMessage)
            line = readlnOrNull()
        }

        var num: Int? = line.toIntOrNull()

        if (num == null || num < leftBorder || num > rightBorder) {
            println(errorMessage)
            return checkRangeNumberInput(leftBorder, rightBorder, errorMessage)
        }
        return num

    }

}