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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlin.random.Random

@Composable
fun OtpScreen(
    navController: NavHostController, data:String?
    ) {
    var text by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<ByteArray?>(null) }
    val otpValue = remember { mutableStateOf("") }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    var phone by remember { mutableStateOf("") }
//    AlertDialog( onDismissRequest = onDismiss ,
//        text = {



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
                "We sent a verification code to ",
        modifier = Modifier.wrapContentWidth().padding(8.dp,top=20.dp),
        style = MaterialTheme.typography.subtitle1,
//        fontWeight = FontWeight.SemiBold

        )
        Text(
            "Your Mobile Number +91$data.",
            modifier = Modifier.wrapContentWidth(),
            style = MaterialTheme.typography.subtitle1,
//            fontWeight = FontWeight.SemiBold

        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top=50.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until 6) {
//                OtpBox(
//                    value = otpValue.value.getOrNull(i)?.toString() ?: "",
//                    onValueChange = { newValue ->
//                        if (newValue.length <= 1) {
//                            otpValue.value =
//                                otpValue.value.take(i) + newValue + otpValue.value.drop(i + 1)
//                            if (newValue.isNotEmpty() && i < 5) {
//                                focusRequesters[i + 1].requestFocus()
//                            }
//                        }
//                    },
//                    focusRequester = focusRequesters[i],
//                    modifier = Modifier.weight(1f)
//                )
                OtpBox(
                    value = otpValue.value.getOrNull(i)?.toString() ?: "",
                    onValueChange = { newValue ->
                        // Handle OTP input and focus movement
                        if (newValue.length <= 1) {
                            otpValue.value =
                                otpValue.value.take(i) + newValue + otpValue.value.drop(i + 1)
                            if (newValue.isNotEmpty() && i < 5) {
                                // Move focus forward if a number is entered
                                focusRequesters[i + 1].requestFocus()
                            } else if (newValue.isEmpty() && i > 0) {
                                // Move focus backward only if the current box is empty
                                focusRequesters[i - 1].requestFocus()
                            }
                        }
                    },
                    focusRequester = focusRequesters[i],
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).width(150.dp).padding(top=20.dp),


            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppColors.SoftPurple,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                navController.navigate("screen2")
            },
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
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .size(50.dp)
            .focusRequester(focusRequester)
            .background(AppColors.Background),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(10.dp),


    )
}


//
//    @Composable
//    fun OtpDialog(phoneNumber: String, onOtpGenerated: (String) -> Unit, onDismiss: () -> Unit) {
////        var otp by remember { mutableStateOf("") }
////        val lastFourDigits =
////            if (phoneNumber.length >= 4) phoneNumber.substring(phoneNumber.length - 4) else ""
////
////        LaunchedEffect(key1 = Unit) {
////            otp = generateOtp()
////            onOtpGenerated(otp)
////        }
//
//        Dialog(onDismissRequest = onDismiss) {
//            Card(elevation = 8.dp) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "OTP Verification",
//                        style = MaterialTheme.typography.h6,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(
//                        text = "Enter the OTP sent to **** **** ",
//                        style = MaterialTheme.typography.body1
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Text(
//                        text = "",
//                        style = MaterialTheme.typography.h4,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Button(onClick = onDismiss) {
//                        Text("OK")
//                    }
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun OtpDialogScreen(phoneNumber: String) {
//        var showOtpDialog by remember { mutableStateOf(true) } // Initially show the dialog
//        var generatedOtp by remember { mutableStateOf("") }
//
//        if (showOtpDialog) {
//            OtpDialog(
//                phoneNumber = phoneNumber,
//                onOtpGenerated = { generatedOtp = it },
//                onDismiss = { showOtpDialog = false }
//            )
//        }
//
//        // You can display the generated OTP or use it for verification here
//        Text("Generated OTP: $generatedOtp")
//    }

//    private fun generateOtp(): String {
//        return Random.nextInt(1000, 9999).toString()
//    }



