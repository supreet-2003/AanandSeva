// AliClient.kt
import io.ktor.client.HttpClient
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

class ApiClient {
    private val client = HttpClient()

     suspend fun login(contactNumber: String): Any? {
        return try {
            println("Phone: $contactNumber")
            val response: HttpResponse = client.post(url = Url("http://10.0.2.2:4000/users/login/$contactNumber"))
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                println("Response Body: $responseBody")
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
    suspend fun saveUserDetails(user: String, id: String): Any? {
        return try {
            val user1 = Json.parseToJsonElement(user)
            val jsonString = Json.encodeToString(user1)

            val response = client.put("http://10.0.2.2:4000/users/$id") {
                contentType(ContentType.Application.Json)
                body = jsonString
            }
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                println("Response Body: $responseBody")
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
}
