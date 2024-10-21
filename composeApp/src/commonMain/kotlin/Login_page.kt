import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.facebook
import anandseva_kmp.composeapp.generated.resources.google
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.twitter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.russhwolf.settings.Settings

val settings: Settings = Settings()
fun storeCache(key: String, value: String){
        settings.putString(key,value)
}

@Composable
@Preview
fun App(navController: NavHostController) {
    val apiClient = remember { ApiClient() }
    val coroutineScope = rememberCoroutineScope()

     suspend fun performLogin(phone: String): User? {
        try {
            val response = apiClient.login(phone)
            println("ressoonse store token $response")
            return response
        } catch (e: Exception) {
            println("Error: $e.message")
            return null
        }
    }

    var phone by remember {
        mutableStateOf("")
    }
    var isValidPhone by remember { mutableStateOf(true) }

    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFAFF2F2), Color(0xFF194B88))
                    )
                ),


        ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    Image(
                        painterResource(Res.drawable.logo),
                        contentDescription = "Login logo",
                        modifier = Modifier.size(70.dp)
                    )
                    Column (verticalArrangement = Arrangement.SpaceAround){
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            text = "AanandSeva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp,

                            )
                        Text(
                            text = "Where Health Meets Convenience.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.SoftPurple,
//                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 150.dp, bottom = 50.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Welcome Back", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    Text(text = "Login to Your Account",fontSize = 15.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(bottom = 70.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                     Text(text = "  Phone number", fontWeight = FontWeight.ExtraBold,fontSize = 18.sp)

                    var isFocused by remember { mutableStateOf(false) }

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                phone = it
                            }
                            isValidPhone = phone.length == 10
                        },
                        placeholder = { Text(text = "Enter Your Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = !isValidPhone,
                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }
                            .fillMaxWidth(0.9f).padding(bottom = 20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = if (isFocused) Color.White else Color.LightGray,
                            focusedBorderColor = MaterialTheme.colors.primary,
                            unfocusedBorderColor = Color.Gray,
                            textColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )


                    if (!isValidPhone && phone.isNotEmpty()) {
                        Text(
                            text = "Please enter a valid 10-digit phone number",
                            color = Color.Red
                        )
                    }

//                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                       , shape = RoundedCornerShape(10.dp),
                        onClick = {
                            coroutineScope.launch {
                                val result = performLogin(phone)
                                println("Result: $result")

                                if(result != null) {
                                    if (result.authToken != null) {
                                        storeCache("auth_token",result.authToken)
                                        token = result.authToken
                                    }
                                    if(result.name != null && result.type != null) {
                                        storeCache("userName",result.name)
                                        storeCache("userType",result.type)
                                        userName = result.name
                                        userType = result.type
                                    }
                                }
                                if(result !== null){
                                    if (!result.isVerified) {
                                        println("err $result")
                                        //navigate to otp
                                    navController.navigate("screen7/${result.contactNumber}/${result.otp}")
                                    } else if (result.name !== null){
                                        navController.navigate("screen2")
                                    } else {
                                        println("Result id:$result")
                                        val user = result.toString()
                                        val id = phone
                                        println("User$user")
                                        navController.navigate("screen4/$id")
                                    }
                                }
                            }
                        },
                        enabled = isValidPhone && phone.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.CenterHorizontally),
                    ) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Forgot Password?",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable { }.align(Alignment.End),
                        )
                        }

                }
            }
        }
    }
//}
