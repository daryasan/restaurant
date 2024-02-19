package restaurant.ui

import restaurant.entity.User

interface OptionChooser {

    fun authorize(): User

    fun getConsoleMenu(user : User)
}