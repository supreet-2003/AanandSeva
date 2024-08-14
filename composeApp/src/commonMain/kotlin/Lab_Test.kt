import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.medicine
import androidx.compose.foundation.Image
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LabTest(navController: NavHostController) {
    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {


        var showDialog1 by remember { mutableStateOf(false) }
        val searchText = remember { mutableStateOf(TextFieldValue("")) }


        Scaffold(
            topBar = {

                Column(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(
                            painterResource(Res.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.padding(
                                top = 5.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        )
                        Text(
                            text = "AanandSeva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(5.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp).padding(vertical = 5.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate("screen5 ")

                                    }
                                )
                        )

                    }

                    Text(
                        "Upload Your Lab Tests",
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black,
                                blurRadius = 3f
                            )
                        ),
                        fontSize = 30.sp
                    )
                }
            },
            bottomBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            navController.navigate("screen2")
                        }),
                        tint = Color.Gray
                    )
                    Image(
                        painterResource(Res.drawable.medicine), contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            navController.navigate("screen5")
                        })
                    )
                    Image(
                        painterResource(Res.drawable.flask),
                        contentDescription = "Lab Test",
                        modifier = Modifier.size(40.dp)
                            .clickable
                                (
                                onClick = { navController.navigate("screen3") })
                    )
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            navController.navigate("screen4")

                        })
                    )


                }

            },
            floatingActionButton = {

                FloatingActionButton(

                    onClick = {
//                            if (showDialog1) {
//                                LabPop(onDismiss = {showDialog1=false})
//                            }
                         showDialog1 = true
                    },
                    backgroundColor = AppColors.Background
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            if (showDialog1) {
                LabPop(onDismiss = { showDialog1 = false })
            }
            Column(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                LabList()
            }

        }
    }
}

//        Surface(
//            color = MaterialTheme.colors.background
//        ) {
//            Column(modifier = Modifier.fillMaxSize()
//            )
//            {
//                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back button", modifier = Modifier
//                    .clickable(onClick = {navController.navigate("screen1")})
//                    .align(Alignment.Start),)
//                Spacer(modifier = Modifier.size(20.dp))
//                Text("Upload Your Tests", modifier = Modifier
//                    .align(Alignment.CenterHorizontally))
//                Spacer(modifier = Modifier.weight(1f))
//                var expanded by remember {mutableStateOf(false)  }
//                var selectedText by remember { mutableStateOf("") }
//                val options = listOf("Option 1", "Option 2", "Option 3")
//                IconButton(onClick = {expanded=true},modifier = Modifier
//                    .align(Alignment.End)
//                    ) {
//                    Icon(imageVector = Icons.Sharp.Add,
//                        contentDescription = null,
//
//                        )
//                }
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
//                ) {
//                    Column( modifier = Modifier.fillMaxWidth()) {
//                        Text("Upload Your Credentials",)
//                        Spacer(modifier = Modifier.size(40.dp))
//
//                        OutlinedTextField(
//                            value = "",
//                            onValueChange = { selectedText = it },
//                            label = { Text("Select an option") },
//                            trailingIcon = {
//                                IconButton(onClick = { expanded = !expanded }) {
//                                    Icon(Icons.Filled.ArrowDropDown, "Dropdown arrow")
//                                }
//                            },
//                            modifier = Modifier.clickable { expanded = true } // Allow clicking the field to open the menu
//                        )
//
//                        DropdownMenu(
//                            expanded = expanded,
//                            onDismissRequest = { expanded = false }
//                        ) {
//                            options.forEach { option ->
//                                DropdownMenuItem(onClick = {
//                                    selectedText=option
//                                    expanded=false
//                                }, text = { Text(option) })
//                            }
//                        }
//                    }
//                }
//
//            }

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



