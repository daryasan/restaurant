package restaurant.DB

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import restaurant.entity.users.User
import restaurant.exceptions.EmptyFileException
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class RestaurantSerializer {

    private val path = "resources/jsonDB/"
    val userPath = "${path}users.json"

    // serialize any type of array in program
    fun <T> serialize(list: MutableList<T>) {

        // choose path
        var pathToWrite: String = path
        if (list.isNotEmpty() && list[0] is User) {
            pathToWrite = userPath
        }

        val jsonArray = jacksonObjectMapper().writeValueAsString(list)
        try {
            File(pathToWrite).bufferedWriter().use { out ->
                out.write(jsonArray)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }


    // deserialize any type of array in program
    fun <T> deserialize(path: String): MutableList<T> {

        try {
            val mapper = jacksonObjectMapper()
            mapper.registerKotlinModule()
            mapper.registerModule(JavaTimeModule())

            val encoded = Files.readAllBytes(Paths.get(path))
            val content = String(encoded, Charsets.UTF_8)
            return mapper.readValue<MutableList<T>>(content)
        } catch (e: IOException) {
            throw EmptyFileException("Seems like there are no users now.")
        }


    }

}