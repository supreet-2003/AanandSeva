import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.example.anandsevakmp.ImageDisplayScreen
//import kotlin.ImagePicker
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MedPop(
    navController: NavController,
    onDismiss:()->Unit,
    onUploadClick: () -> Unit,
    onOrderClick:() -> Unit,
    viewModel: ImagePickerViewModel,
){
    var text by remember { mutableStateOf("") }
//    var selectedImage by remember { mutableStateOf<ByteArray?>(null) }
//    val temp = remember { mutableStateOf(0) }
    val isImagepicked by viewModel.isImagePicked.collectAsState()
    var showImagePicker by remember { mutableStateOf(false) }
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val pickImageLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        selectedImageUri = uri
//    }

    AlertDialog( onDismissRequest = onDismiss ,
        backgroundColor = Color.White,
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                "Upload Your Image",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                fontWeight = FontWeight.Bold
                            )
//                            Button(
//                                modifier = Modifier.wrapContentSize(), onClick = {
//                                    showImagePicker=true
////                                    viewModel.setSho'uldDisplayImage(true)
////                                    onUploadClick()
//                                    navController.navigate("imagepicker")
//
//                                },shape = RoundedCornerShape(15.dp),
//                                colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
//                           ){
//                                Text("Upload")
//                            }
                            if (isImagepicked) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Uploaded",
                                    tint = Color.Green,
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                Button(
                                modifier = Modifier.wrapContentSize(), onClick = {
//                                    viewModel.onImagePicked()
//                                    viewModel.setSho'uldDis       playImage(true)
//                                    onUploadClick()
                                    navController.navigate("imagepicker")
//                                        showImagePicker=true

                                },shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                           ){
                                Text("Upload")
                            }
                            }
                        }
                        OutlinedTextField(
                            value = text, onValueChange = {
                                    newText ->
                                text = newText
                                viewModel.updateTextInput(newText)
                                                          },
                            modifier = Modifier.fillMaxWidth().height(250.dp).padding(8.dp),
                            label = {"Description"}, shape = RoundedCornerShape(15.dp)
                        )

//                        if (showImagePicker) {
//                            val imageUri by viewModel.imageUri.collectAsState()
//                            imageUri?.let { uri ->
//                                ImageDisplayScreen(navController = null, imageUri = uri)
//                            }
//                        }



                    }
            }, confirmButton = {

                    Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                        , shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            viewModel.updateStatus("Ordered")
                            onOrderClick()
                            onDismiss()

                        },
                    ) {
                        Text(text = "ORDER")

                    }
                }


            )
//    if(showImagePicker){
//        showImagePicker=false
//    }

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