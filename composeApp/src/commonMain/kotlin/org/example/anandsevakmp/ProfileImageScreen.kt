package org.example.anandsevakmp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
expect fun ProfileImageScreen(
    navController: NavController?,
    imageUri: String?
)

expect fun dialPhoneNumber(phoneNumber: String)