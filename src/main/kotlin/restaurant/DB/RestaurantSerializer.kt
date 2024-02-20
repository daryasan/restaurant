package restaurant.DB

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import restaurant.entity.Dish
import restaurant.entity.Order
import restaurant.entity.User
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class RestaurantSerializer {

    private val path = "src/main/resources/jsonDB/"
    private val userPath = "${path}users.json"
    private val menuPath = "${path}menu.json"
    private val ordersPath = "${path}orders.json"

    fun serializeUsers(list: MutableList<User>) {

        val jsonArray = jacksonObjectMapper().writeValueAsString(list)
        try {
            File(userPath).bufferedWriter().use { out ->
                out.write(jsonArray)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }


    fun deserializeUsers(): MutableList<User> {
        return try {
            val encoded = Files.readAllBytes(Paths.get(userPath))
            val content = String(encoded, Charsets.UTF_8)
            jacksonObjectMapper().readValue<MutableList<User>>(content)
        } catch (e: IOException) {
            mutableListOf()
        }
    }

    fun serializeDishes(list: MutableList<Dish>) {

        val jsonArray = jacksonObjectMapper().writeValueAsString(list)
        try {
            File(menuPath).bufferedWriter().use { out ->
                out.write(jsonArray)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun deserializeDishes(): MutableList<Dish> {
        return try {
            val encoded = Files.readAllBytes(Paths.get(menuPath))
            val content = String(encoded, Charsets.UTF_8)
            jacksonObjectMapper().readValue<MutableList<Dish>>(content)
        } catch (e: IOException) {
            mutableListOf()
        }
    }

    fun serializeOrders(list: MutableList<Order>) {

        val jsonArray = jacksonObjectMapper().writeValueAsString(list)
        try {
            File(ordersPath).bufferedWriter().use { out ->
                out.write(jsonArray)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun deserializeOrders(): MutableList<Order> {
        return try {
            val encoded = Files.readAllBytes(Paths.get(ordersPath))
            val content = String(encoded, Charsets.UTF_8)
            jacksonObjectMapper().readValue<MutableList<Order>>(content)
        } catch (e: IOException) {
            mutableListOf()
        }
    }

}