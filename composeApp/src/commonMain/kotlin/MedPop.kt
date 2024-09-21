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
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import java.io.FileNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun getGoogleDriveService(): Drive {
    val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream("aanandseva-ccc66d6e586e.json")

    // Ensure the stream is not null
    requireNotNull(resourceAsStream) { "Resource file not found: aanandseva-ccc66d6e586e.json" }

    val credential: Credential = GoogleCredential.fromStream(resourceAsStream)
        .createScoped(listOf(DriveScopes.DRIVE_FILE))

    return Drive.Builder(
        com.google.api.client.http.javanet.NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        credential
    ).setApplicationName("Drive API Kotlin").build()
}


suspend fun uploadFileToGoogleDrive(service: Drive, filePath: String?, folderId: String? = null): File? {
    return withContext(Dispatchers.IO) {
        try {
            // Check if file exists
            val file = java.io.File(filePath)
            println("File exists: ${file.exists()}, Path: $filePath")

            if (!file.exists()) {
                println("File not found at path: $filePath")
                return@withContext null
            }

            // Create file metadata
            val fileMetadata = File().apply {
                name = file.name
                parents = folderId?.let { listOf(it) }
            }

            println("Uploading file with name: ${fileMetadata.name}")

            // Create file content (MIME type can be adjusted based on file type)
            val mediaContent = FileContent("image/jpeg", file)
            println("Media content created with file: ${file.absolutePath}")

            // Extra logging for the file
            println("File content type: ${mediaContent.type}")
            println("File size: ${file.length()} bytes")

            // Upload the file to Google Drive
            val uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink, webContentLink")
                .execute()

            println("File uploaded successfully: ID = ${uploadedFile.id}")
            println("Web View Link: ${uploadedFile.webViewLink}")
            uploadedFile
        } catch (e: FileNotFoundException) {
            println("Error: File not found: ${e.message}")
            null
        } catch (e: com.google.api.client.googleapis.json.GoogleJsonResponseException) {
            println("Google API error: ${e.statusCode} - ${e.details}")
            null
        } catch (e: Exception) {
            println("General error during file upload: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}

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

    val driveService = getGoogleDriveService()

    fun handleFileUpload() {
        println("Image picked: $isImagePicked")

        if (isImagePicked) {
            println("Actual file----: $imageFilePath")

            GlobalScope.launch(Dispatchers.Main) {
                val uploadedFile = uploadFileToGoogleDrive(driveService, imageFilePath?.imagePath, "AanandSeva")
                println("------uploaded file------------$uploadedFile")
                uploadedFile?.let {
                    println("File uploaded successfully!")
                    println("File ID: ${it.id}")
                    println("Web View Link: ${it.webViewLink}")
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
