import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.lightColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun OtpScreen(
    navController: NavHostController, contact:String?, otp:Int?
    ) {
    val apiClient = remember { ApiClient() }
    val otpValue = remember { mutableStateOf("") }
    val focusRequesters = remember { List(4) { FocusRequester() } }
    var isInvalidOtp = false
    val coroutineScope = rememberCoroutineScope()


    suspend fun verifyOtp() {
        print(otpValue)
        println(otp)
        println(otpValue.value.toInt() ==otp)
         if(otpValue.value.toInt() ==otp){
             val response = apiClient.verifyOtp(contact,otpValue.value.toInt())
             if (response != null && response.authToken != null) {
                 storeCache("auth_token",response.authToken)
                 storeCache("contact",response.contactNumber)
             }
             navController.navigate("screen4/$contact")
         }
         else{
            isInvalidOtp = true
             println("invalid otp ${isInvalidOtp}${otpValue.value !== ""} && ${otpValue.value.toInt() > 999}")
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(AppColors.Background), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(125.dp)

        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back button",
                modifier = Modifier.clickable(onClick = { navController.navigate("screen1") })
            )
            Text("Verify Account", modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Black)
        }

        Text("Enter Your Verification Code",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=100.dp),
//            Arrangement = Arrangement.Center,
//            style = MaterialTheme.typography.h5,
            style=TextStyle(
                color = Color.Black,
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = Color.Black,
                    blurRadius = 3f
                )
            ), fontSize = 30.sp,
            fontWeight = FontWeight.Bold,


        )
        Text(
                "We have sent a verification code to +91-$contact",
        modifier = Modifier.wrapContentWidth().padding(8.dp,top=20.dp),
        style = MaterialTheme.typography.subtitle1,
//        fontWeight = FontWeight.SemiBold

        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top=50.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until 4) {
                OtpBox(
                    value = otpValue.value.getOrNull(i)?.toString() ?: "",
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            otpValue.value =
                                otpValue.value.take(i) + newValue + otpValue.value.drop(i + 1)
                            if (newValue.isNotEmpty() && i < 3) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        }
                    },
                    focusRequester = focusRequesters[i],
//                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (isInvalidOtp && otpValue.value !== "" && otpValue.value.toInt() > 999) {
            Text(
                text = "Invalid OTP provided!",
                color = Color.Red
            )
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).width(150.dp).padding(top=20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.SoftPurple,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = { coroutineScope.launch {verifyOtp()} },
        ) {
            Text(text = "VERIFY")
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(top=20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Didn't Receive Code? ",)
            Text("Resend Again", color = AppColors.SoftPurple,fontWeight = FontWeight.SemiBold,)
        }
    }

}


@Composable
fun OtpBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .height(64.dp)
            .width(56.dp)
            .focusRequester(focusRequester)
            .background(AppColors.Background),
        textStyle = TextStyle(
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
//            color = Color.Black
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(10.dp)
    )
}