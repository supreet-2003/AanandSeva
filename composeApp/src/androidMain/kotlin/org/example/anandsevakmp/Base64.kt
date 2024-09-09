import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.io.InputStream

@Composable
fun ImagePickerAndConvertToBase64() {
    val context = LocalContext.current
    var base64String by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Convert image URI to Base64
            base64String = convertImageUriToBase64(context, it)
            Log.d("Base64Image", base64String ?: "Conversion failed")
        }
    }

    // Trigger image selection
    Button(onClick = {
        imagePickerLauncher.launch("image/*")
    }) {
        Text("Select Image")
    }

    // Display the Base64 string if available
    base64String?.let {
        Text("Base64: $it")
    }
}

fun convertImageUriToBase64(context: Context, imageUri: Uri): String? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
    val bytes = inputStream?.readBytes()
    inputStream?.close()  // Close the InputStream after use
    return bytes?.let { Base64.encodeToString(it, Base64.DEFAULT) }
}
