package org.example.anandsevakmp

import App
import HomeScreen
import ImagePickerViewModel
import LabTest
import MedOrder
import MedScreen


import Navigation

import UserDetails
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.compose.rememberNavController

object ContextHolder {
    lateinit var appContext: Context
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
             ContextHolder.appContext = this
            AppAndroidPreview()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    ImagePicker()
//    Navigation(ImagePickerViewModel())
App(navController = rememberNavController())
}

