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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(navController: NavHostController) {


    var phone by remember {
        mutableStateOf("")
    }
    var pass by remember {
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
                        .padding(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    Image(
                        painterResource(Res.drawable.logo),
                        contentDescription = "Login logo",
                        modifier = Modifier.size(70.dp)
                    )
                    Text(
                        text = "AanandSeva",
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        modifier = Modifier.padding(10.dp)
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

                    OutlinedTextField(value = phone, onValueChange = {
                        phone = it
                    }, label = { Text(text = "Enter Your Phone Number") })
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



                    Button(
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            navController.navigate("screen4")
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
