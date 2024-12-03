import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun BookLabTest(
    navController: NavController,
    onDismiss:()->Unit,
    onUploadClick: () -> Unit,
    viewModel: ImagePickerViewModel
){
//    val imagePicker = ImagePicker()
//    val coroutineScope = rememberCoroutineScope() // Create a CoroutineScope
//    val imageData by imagePicker.pickImage().collectAsState(initial = null)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptions by remember { mutableStateOf(listOf<String>()) }
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var customOption by remember { mutableStateOf("") }
    var requestCall by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val imageFilePath by viewModel.imageData.collectAsState()
    val apiClient = remember { ApiClient() }
    val isImagepicked by viewModel.isImagePicked.collectAsState()

     fun handleFileUpload() {
        navController.navigate("screen3")
        runBlocking {
            loading.value = true
            if (isImagepicked) {
                GlobalScope.launch(Dispatchers.Main) {
                    val uploadedFile =
                        uploadFileToGoogleDrive(driveService, imageFilePath?.imagePath, null)
                    uploadedFile?.let {
                        println("File uploaded successfully!")
                        println("File ID: ${it.id}")
                        println("Web View Link: ${it.webViewLink}")
                        makeFilePublic(driveService, it.id)
                        val link = it.webViewLink
                        val name = it.id
                        try {
                            runBlocking {
                                var json = ""
                                if(text != "")
                                    json =
                                        """{"file": { "fileName": "$name", "url": "$link"}, "comments": {"text":"$text","date":"","commentedBy":"$userName"}, "orderedBy": "$userName", "orderedOn": "", "orderType": "labtest", "orderStatus": "Ordered"}"""
                                else
                                    json =
                                    """{"file": { "fileName": "$name", "url": "$link"},"orderedBy": "$userName", "orderedOn": "", "orderType": "labtest", "orderStatus": "Ordered"}"""
                                val response = listOf(apiClient.saveOrder(json))
                                _medicineOrders.value = fetchOrders(apiClient,"labtest")
                            }
                            loading.value = false
                            refreshData.value = "true"
                            viewModel.resetImagePicked()
                        } catch (e: Exception) {
                            println("Error: $e")
                        }
                    } ?: println("File upload failed!")

                }
            } else {
                runBlocking {
                    val json =
                        """{"comments": {"text":"$text","date":"","commentedBy":"$userName"}, "orderedBy": "$userName", "orderedOn": "", "orderType": "labtest", "orderStatus": "Ordered"}"""
                    val response = listOf(apiClient.saveOrder(json))
                    _medicineOrders.value = fetchOrders(apiClient, "labtest")
                }
                loading.value = false
                refreshData.value = "true"
                viewModel.resetImagePicked()
            }
        }
    }


    AlertDialog( onDismissRequest =  onDismiss ,
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
                        "Upload Your Prescription",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Bold
                    )
                    if (isImagepicked) {
                        Text("${viewModel.imageData.value?.imageName}")
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "${viewModel.imageData.value}",
                            tint = Color.Blue,
                            modifier = Modifier.size(30.dp)
                                .clickable {
                                    viewModel.resetImagePicked()
                                    navController.navigate("imagepicker")
                                }
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "${viewModel.imageData.value}",
                            tint = Color.Red,
                            modifier = Modifier.size(30.dp)
                                .clickable {
                                    viewModel.resetImagePicked()
                                }
                        )
                    } else {

                        Button(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                navController.navigate("imagepicker")
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = AppColors.SoftPurple,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Upload")
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Request a call from Compounder")
                    Switch(
                        checked = requestCall,
                        onCheckedChange = { requestCall = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Blue,
                            uncheckedThumbColor = Color.Gray
                        )
                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    // Dropdown to select options
                    OutlinedTextField(
                        value = customOption,
                        onValueChange = { customOption = it },
                        label = { Text("Select tests") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = true }
                            )
                        },
                        readOnly = true // Dropdown should be readonly
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Predefined options
                        options.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    if (!selectedOptions.contains(option)) {
                                        selectedOptions = selectedOptions + option
                                    }
                                    expanded = false
                                }
                            ) {
                                Text(text = option)
                            }
                        }

                        // Custom value input
                        DropdownMenuItem(
                            onClick = {
                                if (customOption.isNotBlank() && !selectedOptions.contains(customOption)) {
                                    selectedOptions = selectedOptions + customOption
                                }
                                customOption = "" // Clear input after selection
                                expanded = false
                            }
                        ) {
                            Row {
                                Text("Enter other test:")
                                OutlinedTextField(
                                    value = customOption,
                                    onValueChange = { customOption = it },
                                    modifier = Modifier
                                        .width(180.dp)
                                        .padding(2.dp)
                                        .background(Color.White)
                                )
                                Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.Primary,contentColor = Color.White)
                                    , shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(end = 8.dp),
                                    enabled = customOption != "",
                                    onClick = {
                                        if (customOption.isNotEmpty() && customOption !in selectedOptions) {
                                            selectedOptions = selectedOptions + customOption
                                        }
                                        customOption = ""
                                        expanded = false
                                    }
                                ) {
                                    Text(text = "Add")
                                }
                            }
                        }
                    }
                }

                    Text("Selected tests:")
                    val listState = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 100.dp),
                        state = listState // Attach the state for observing scroll
                    ) {
                        // Display selected options with a cross button to remove
                        if (selectedOptions.isNotEmpty()) {
                            items(selectedOptions) { option ->
                                Row(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .background(MaterialTheme.colors.surface)
                                        .fillMaxWidth()
                                ) {
                                    Text(text = option, modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        modifier = Modifier
                                            .clickable {
                                                selectedOptions = selectedOptions.filter { it != option }
                                            }
                                            .padding(4.dp)
                                    )
                                }
                            }
                        } else {
                            item {
                                Text("No tests selected", color = Color.Gray, modifier = Modifier.padding(4.dp))
                            }
                        }
                    }

                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        viewModel.updateTextInput(newText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    label = { Text("Comment") },
                    shape = RoundedCornerShape(15.dp)
                )
            }
        }, confirmButton = {

            Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
                , shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    handleFileUpload()
                },
            ) {
                Text(text = "SUBMIT")

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