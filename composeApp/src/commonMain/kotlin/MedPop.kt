import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
//import kotlin.ImagePicker
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MedPop(
    onDismiss:()->Unit
){
//    val imagePicker = ImagePicker()
//    val coroutineScope = rememberCoroutineScope() // Create a CoroutineScope
//    val imageData by imagePicker.pickImage().collectAsState(initial = null)

    AlertDialog( onDismissRequest = onDismiss ,
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row {
                            Text(
                                "Upload Your Image",
                                modifier = Modifier.padding(top = 30.dp, start = 10.dp,),
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                modifier = Modifier.align(Alignment.CenterVertically), onClick = {
//                                    coroutineScope.launch {
//                                        imagePicker.pickImage().collectLatest { imageData ->
//                                            selectedImage = imageData
//                                        }
//                                    }
                                }
                           ){
                                Text("Click to Upload")
                            }
                        }
                        OutlinedTextField(
                            value = "", onValueChange = {},
                            modifier = Modifier.fillMaxWidth().height(400.dp).padding(8.dp),
                            label = {"Description"}, shape = RoundedCornerShape(15.dp)
                        )


                    }
            }, confirmButton = {

                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                        , shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            onDismiss()
                        },
                    ) {
                        Text(text = "ORDER")

                    }
                }


            )

    }


//@Preview
//@Composable
//fun PopUpScreen(){
//Column (
//modifier = Modifier.fillMaxSize()
//){
//    Text("Upload Your Image", modifier = Modifier.padding(top = 30.dp, start = 10.dp, ), fontWeight = FontWeight.Bold)
//    OutlinedTextField(
//        value = "", onValueChange = {},
//        modifier = Modifier.fillMaxSize().height(400.dp).padding(8.dp),
//        label = {"Description"}, shape = RoundedCornerShape(15.dp)
//    )
//
//
//}
//
//}