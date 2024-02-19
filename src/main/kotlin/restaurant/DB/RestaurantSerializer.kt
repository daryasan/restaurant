package restaurant.DB

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import restaurant.entity.User
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class RestaurantSerializer {

    private val path = "src/main/resources/jsonDB/"
    val userPath = "${path}users.json"

    // serialize any type of array in program
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


    // deserialize any type of array in program
    fun deserializeUsers(): MutableList<User> {

        try {
            val mapper = jacksonObjectMapper()
            mapper.registerKotlinModule()
            mapper.registerModule(JavaTimeModule())

            val encoded = Files.readAllBytes(Paths.get(userPath))
            val content = String(encoded, Charsets.UTF_8)
            return mapper.readValue<MutableList<User>>(content)
        } catch (e: IOException) {
            return mutableListOf<User>()
        }


    }

}