package org.example.anandsevakmp

import ImagePickerViewModel
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
actual fun ImagePickerScreen(navController: NavController, viewModel: ImagePickerViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Create a launcher for picking an image from the gallery
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Launch a coroutine to call the suspend function
            coroutineScope.launch {
                // Retrieve the file path from the URI
                val imagePath = getImageFilePathFromUri(context, uri)
                if (imagePath != null) {
                    // Update ViewModel with the file path and the picked image URI
                    viewModel.setImagePicked(true, imagePath)
                }
                // Navigate back to the previous screen
                navController.popBackStack()
            }
        } ?: run {
            // If no image was picked, simply pop back to the previous screen
            navController.popBackStack()
        }
    }

    // Automatically trigger the image picker when the screen loads
    LaunchedEffect(Unit) {
        pickImageLauncher.launch("image/*")
    }
}

// Utility function to retrieve the file path from a URI
suspend fun getImageFilePathFromUri(context: android.content.Context, uri: Uri): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        if (it.moveToFirst()) {
            return it.getString(columnIndex)
        }
    }
    return null
}
