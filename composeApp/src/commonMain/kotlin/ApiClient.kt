// AliClient.kt
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class User(
    val _id: String,
    val name: String,
    val clinicAddress: String,
    val contactNumber: String,
    val specialization: List<String>,
    val fee: String,
    val experience: Int,
    val authToken: String,
    val isVerified: Boolean
)

class ApiClient {
    private val client = HttpClient()
    private val ip = "192.168.1.85"

     suspend fun login(contactNumber: String): User? {
        return try {
            println("Phone: $contactNumber")
            val response: HttpResponse = client.post(url = Url("http://$ip:4000/users/login/$contactNumber"))
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                val gson = Gson()
                val itemType = object : TypeToken<User>() {}.type
                val user: User = gson.fromJson(responseBody, itemType)
                return user
            } else {
                println("Error: ${response.status}")
                return null
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun saveUserDetails(user: String, id: String): Any? {
        return try {
            val user1 = Json.parseToJsonElement(user)
            val jsonString = Json.encodeToString(user1)

            val response = client.put("http://$ip:4000/users/$id") {
                contentType(ContentType.Application.Json)
                body = jsonString
            }
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                return responseBody
            } else {
                println("Error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    suspend fun fetchAllDoctors(): List<Doctor>? {
        return try {
            val response: HttpResponse = client.get(url = Url("http://$ip:4000/doctors"))
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                val gson = Gson()
                val itemType = object : TypeToken<List<Doctor>>() {}.type
                val doctors: List<Doctor> = gson.fromJson(responseBody, itemType)
                return doctors
            } else {
                println("Error: ${response.status}")
                return null
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}
