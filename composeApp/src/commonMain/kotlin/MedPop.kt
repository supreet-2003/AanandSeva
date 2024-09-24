import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val driveService = getGoogleDriveService()

// Composable function for the dialog
@Composable
fun MedPop(
    navController: NavController,
    onDismiss: () -> Unit,
    onUploadClick: () -> Unit,
    onOrderClick: () -> Unit,
    viewModel: ImagePickerViewModel,
) {
    var text by remember { mutableStateOf("") }
    val isImagePicked by viewModel.isImagePicked.collectAsState()
    val imageFilePath by viewModel.imageData.collectAsState()
    val apiClient = remember { ApiClient() }

    fun handleFileUpload() {

        if (isImagePicked) {
            GlobalScope.launch(Dispatchers.Main) {
                val uploadedFile = uploadFileToGoogleDrive(driveService, imageFilePath?.imagePath, null)
                uploadedFile?.let {
                    println("File uploaded successfully!")
                    println("File ID: ${it.id}")
                    println("Web View Link: ${it.webViewLink}")
                    makeFilePublic(driveService, it.id)
                    val link = it.webViewLink
                    val name = it.id
                    try {
                        val json = """{"file": { "fileName": "$name", "url": "$link"}, "comments": {"text":"$text","date":"","commentedBy":"$userName"}, "orderedBy": "$userName", "orderedOn": "", "orderType": "medicine", "orderStatus": "Ordered"}"""
                        val response = apiClient.saveOrder(json)
                        println("Response: $response")
                    } catch (e: Exception) {
                        println("Error: $e.message")
                    }
                } ?: println("File upload failed!")

            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        backgroundColor = Color.White,
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Upload Your Image",
                        fontWeight = FontWeight.Bold
                    )

                    if (isImagePicked) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Uploaded",
                            tint = Color.Green,
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Button(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                navController.navigate("imagepicker")
                                handleFileUpload()
                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Upload")
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
                        .height(250.dp)
                        .padding(8.dp),
                    label = { Text("Description") },
                    shape = RoundedCornerShape(15.dp)
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    onOrderClick()
                    onDismiss()
                    handleFileUpload()
                }
            ) {
                Text(text = "ORDER")
            }
        }
    )
}
