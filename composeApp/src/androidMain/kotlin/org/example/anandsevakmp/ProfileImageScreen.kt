package org.example.anandsevakmp

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
//import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import android.content.Intent
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun ProfileImageScreen(navController: NavController?, imageUri: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
    ) {
        if (imageUri != null) {
            val decodedUri = Uri.decode(imageUri)
            AsyncImage(
                model = Uri.parse(decodedUri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        } else {
            Text("Please try again later...")
        }
    }
}

actual fun dialPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    ContextHolder.appContext.startActivity(intent)
}

