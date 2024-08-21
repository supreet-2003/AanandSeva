import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.facebook
import anandseva_kmp.composeapp.generated.resources.google
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.twitter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
fun storeToken(token: String){
    settings.putString("auth_token",token)
}

@Composable
@Preview
fun App(navController: NavHostController) {
    val apiClient = remember { ApiClient() }
    val coroutineScope = rememberCoroutineScope()

     suspend fun performLogin(phone: String): User? {
        try {
            val response = apiClient.login(phone)
            if (response != null && response.authToken != null) {
                storeToken(response.authToken)
            }
            return response
        } catch (e: Exception) {
            println("Error: $e.message")
            return null
        }
    }


    var phone by remember {
        mutableStateOf("")
    }
    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
//            Box(
//                modifier = Modifier.background(Color.Blue)
//            ) {


                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    Image(
                        painterResource(Res.drawable.logo),
                        contentDescription = "Login logo",
                        modifier = Modifier.size(70.dp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        text = "AanandSeva",
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,

                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 150.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Welcome Back", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Login to Your Account",)
                    Spacer(modifier = Modifier.padding(5.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(text = "Enter Your Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
//                    OutlinedTextField(
//                        value = pass,
//                        onValueChange = {
//                            pass = it
//                        },
//                        label = { Text(text = "Password") },
//                        visualTransformation = PasswordVisualTransformation()
//                    )
//                    Spacer(modifier = Modifier.padding(5.dp))



                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                       , shape = RoundedCornerShape(10.dp),
                        onClick = {
                            coroutineScope.launch {
                                val result = performLogin(phone)
                                println("Result: $result")
                                if(result !== null){
                                    if (!result.isVerified) {
                                        println("err $result")
                                        //navigate to otp
//                                    navController.navigate("screen7") // Navigate on successful login
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
                    ) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Forgot Password?",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { })
                    Spacer(modifier = Modifier.padding(25.dp))
                    Text(text = "Or Sign In With")
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.facebook), modifier = Modifier
                                .size(45.dp)
                                .clickable { }, contentDescription = "facebook logo"
                        )
                        Image(
                            painter = painterResource(Res.drawable.twitter), modifier = Modifier
                                .size(45.dp)
                                .clickable { }, contentDescription = "twitter logo"
                        )
                        Image(
                            painter = painterResource(Res.drawable.google), modifier = Modifier
                                .size(45.dp)
                                .clickable { }, contentDescription = "google logo"
                        )
                    }
                }
            }
        }
    }
//}
