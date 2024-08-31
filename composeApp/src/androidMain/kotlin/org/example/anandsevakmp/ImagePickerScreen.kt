package org.example.anandsevakmp

import ImagePickerViewModel
import MedPop
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
actual fun ImagePickerScreen(navController: NavController, viewModel: ImagePickerViewModel) {
    val context = LocalContext.current
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val encodedUri = Uri.encode(it.toString())
            viewModel.setImageUri(encodedUri)
            navController.popBackStack()
//            navController.navigate("med"
//            )

        } ?: run {
            navController.popBackStack()

        }
    }

        LaunchedEffect(Unit) {
            pickImageLauncher.launch("image/*")
        }

    }
