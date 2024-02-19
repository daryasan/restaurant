package restaurant.ui

import restaurant.exceptions.UIException
import restaurant.ui.enums.AdminOptions
import restaurant.ui.enums.AuthOptions

class Checker {

    // check inputs
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

    fun checkIntInput(
        errorMessage: String =
            "Wrong number entered. Please try again:"
    ): Int {
        var line = checkStringInput()
        while (line.toIntOrNull() == null && line.toIntOrNull()!! < 0) {
            println(errorMessage)
            line = checkStringInput()
        }
        return line.toInt()
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