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
    val type: String,
    val clinicAddress: String,
    val contactNumber: String,
    val specialization: List<String>,
    val fee: String,
    val experience: Int,
    val authToken: String,
    val isVerified: Boolean,
    val otp: String
)

data class orderFile (
    val url: String,
    val fileName: String
)

data class comments (
    val text: String,
    val date: String,
    val commentedBy: String
)

data class Order (
    val _id: String,
    val file: orderFile,
    var imageStorageLink: String,
    val comments : Array<comments>,
    val orderedBy: String,
    val orderedOn: String,
    val orderType: String,
    var orderStatus: String
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

    suspend fun verifyOtp(contactNumber: String?,otp: Number): User? {
        return try {
            val response: HttpResponse = client.post(url = Url("http://$ip:4000/users/verify/$contactNumber/$otp"))
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

    @OptIn(InternalAPI::class)
    suspend fun saveOrder(order: String): Any? {
        return try {
            val order1 = Json.parseToJsonElement(order)
            val jsonString = Json.encodeToString(order1)

            print("string----$jsonString")

            val response = client.post("http://$ip:4000/orders") {
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

    suspend fun fetchMedicineOrders(): List<Order>? {
        return try {
            val response: HttpResponse = client.get(url = Url("http://$ip:4000/orders"))
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                val gson = Gson()
                val itemType = object : TypeToken<List<Order>>() {}.type
                val orders: List<Order> = gson.fromJson(responseBody, itemType)
                return orders
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
