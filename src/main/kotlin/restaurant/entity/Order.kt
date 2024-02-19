package restaurant.entity

class Order(
    var dishes: MutableMap<Dish, Int>
) {
    val cost: Int
        get() {
            var sum = 0
            for (d in dishes) {
                sum += d.key.price * d.value
            }
            return sum
        }
}