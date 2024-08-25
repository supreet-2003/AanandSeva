package org.example.anandsevakmp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
expect fun ImageDisplayScreen(
    navController: NavController?,
    imageUri: String?
)
