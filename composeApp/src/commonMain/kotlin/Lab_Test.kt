import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.lightColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LabTest(navController: NavHostController) {
    MaterialTheme(
        colors = lightColors(
            background = AppColors.SoftBlue
        )
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()
            )
            {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back button", modifier = Modifier
                    .clickable(onClick = {navController.navigate("screen1")})
                    .align(Alignment.Start),)
                Spacer(modifier = Modifier.size(20.dp))
                Text("Upload Your Tests", modifier = Modifier
                    .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.weight(1f))
                var expanded by remember {mutableStateOf(false)  }
                var selectedText by remember { mutableStateOf("") }
                val options = listOf("Option 1", "Option 2", "Option 3")
                IconButton(onClick = {expanded=true},modifier = Modifier
                    .align(Alignment.End)
                    ) {
                    Icon(imageVector = Icons.Sharp.Add,
                        contentDescription = null,

                        )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
                ) {
                    Column( modifier = Modifier.fillMaxWidth()) {
                        Text("Upload Your Credentials",)
                        Spacer(modifier = Modifier.size(40.dp))

                        OutlinedTextField(
                            value = "",
                            onValueChange = { selectedText = it },
                            label = { Text("Select an option") },
                            trailingIcon = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(Icons.Filled.ArrowDropDown, "Dropdown arrow")
                                }
                            },
                            modifier = Modifier.clickable { expanded = true } // Allow clicking the field to open the menu
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    selectedText=option
                                    expanded=false
                                }, text = { Text(option) })
                            }
                        }
                    }
                }

            }}
    }
}
@Composable
fun uploadLabTestScreen(){

}

//interface FileUploadHandler {
//    fun pickFile()
//    fun uploadFile(uri: String)
//}
//
//@Preview
//    @Composable
//    fun UploadLabTestScreen(fileUploadHandler: FileUploadHandler) {
//        var selectedFileName by remember { mutableStateOf("No file selected") }
//        var selectedFileUri by remember { mutableStateOf<String?>(null) }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = { fileUploadHandler.pickFile() }) {
//                Text("Select Lab Test File")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Text("Selected File: $selectedFileName")
//
//            if (selectedFileUri != null) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(onClick = { fileUploadHandler.uploadFile(selectedFileUri!!) }) {
//                    Text("Upload Lab Test")
//                }
//            }
//        }
//    }



