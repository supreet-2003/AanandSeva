package org.example.anandsevakmp

import ImagePickerViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
expect fun ImagePickerScreen(
    navController: NavController,
    viewModel: ImagePickerViewModel
)