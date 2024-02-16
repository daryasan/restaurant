package restaurant.ui

import restaurant.entity.users.User

interface OptionChooser {

    fun authorize() : User

}