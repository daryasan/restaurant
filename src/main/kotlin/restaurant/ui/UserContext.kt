package restaurant.ui

class UserContext(var strategy: UserStrategy) {
    fun showMenu() {
        strategy.getMenu()
    }

}