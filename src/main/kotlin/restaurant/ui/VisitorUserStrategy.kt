package restaurant.ui

import currentUser
import restaurant.dao.RuntimeOrderDAO
import restaurant.entity.Order
import restaurant.exceptions.OrderException
import restaurant.ui.enums.EnumChooser
import restaurant.ui.enums.OrderStatus
import restaurant.ui.enums.VisitorOptions
import kotlin.system.exitProcess

class VisitorUserStrategy : UserStrategy {

    private val console: ConsoleOptionChooser = ConsoleOptionChooser()
    private val checker: Checker = Checker()
    private val enumChooser = EnumChooser()
    private val orderDAO = RuntimeOrderDAO()

    override fun getMenu() {

        checkOrder()

        console.printSeparator()
        println("WHAT DO YOU WANT TO DO?")
        println("1.MAKE AN ORDER")
        println("2.SEE YOUR ORDER STATUS")
        println("3.CANCEL YOUR ORDER")
        println("4.LOG OUT")
        println("5.EXIT")
        console.printSeparator()

        checkOrder()

        when (enumChooser.checkVisitorOptions()) {
            VisitorOptions.MAKE_ORDER -> {
                val order = constructOrder()
                val orderThread = Thread(orderDAO.makeOrder(order))
                orderThread.start()
                println("Thank you for your order! It will take ${order.difficulty()} minutes to get it ready!")
            }

            VisitorOptions.SEE_ORDER -> {
                try {
                    val order = orderDAO.findOrderForCurrentUser()
                    println(order.toString())
                } catch (e: OrderException) {
                    println(e.message)
                }
            }

            VisitorOptions.CANCEL_ORDER -> {
                if (orderDAO.userHasOrder()) {
                    safeDelete()
                } else {
                    println("No orders for now!")
                }

            }

            VisitorOptions.LOG_OUT -> {
                currentUser = console.authorize()
                return
            }

            VisitorOptions.EXIT -> {
                print("Exiting program...")
                exitProcess(0)
            }
        }
        console.printSeparator()
    }

    private fun safeDelete() {
        println("Your current order is:")
        val order = orderDAO.findOrderForCurrentUser()
        println(order.toString())
        println("Are you sure you want to cancel your current order?")
        if (checker.checkYesOrNo()) {
            orderDAO.deleteCurrentUserOrder()
        }
        println("Successfully cancelled!")
    }

    private fun constructOrder(): Order {
        if (orderDAO.userHasOrder()) {
            println("Adding to your current order...")
        }
        val order = orderDAO.initOrder()
        do {
            val dish = console.getItemFromMenuByConsole()
            println("How many of ${dish.name} should we make?")
            val amount = checker.checkRangeNumberInput(
                1, dish.currentAmount,
                "Sorry, seems like we don't have this much of ${dish.name} now. " +
                        "You can order under ${dish.currentAmount}!"
            )
            orderDAO.addDishToOrder(order, dish, amount)
            println("Added! Your total will be ${order.cost()}₽")
            println("Anything else?")
        } while (checker.checkYesOrNo())
        return order
    }

    fun checkOrder() {
        if (orderDAO.userHasOrder() &&
            orderDAO.findOrderForCurrentUser().status == OrderStatus.READY
        ) {
            val order = orderDAO.findOrderForCurrentUser()
            println("Your order is ready! Your total is ${order.cost()}₽")
            println("Enter ${order.cost()} to pay:")
            checker.checkRangeNumberInput(
                order.cost(), order.cost(),
                "Sorry, wrong amount to pay! Try again:"
            )
            orderDAO.pay(order)
            println("Successfully paid!")
        }
    }

}