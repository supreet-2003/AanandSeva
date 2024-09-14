package org.example.anandsevakmp

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
//import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
actual fun  ImageDisplayScreen(navController: NavController?, imageUri: String?) {
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//    Column() {
//        ImagePicker { uri ->
//            imageUri = uri
//        }
//
//        imageUri?.let { uri ->
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(uri)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "Picked Image",
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(6.dp))

    ) {
        if (imageUri != null) {
            val decodedUri = Uri.decode(imageUri)
            AsyncImage(
                model = Uri.parse(decodedUri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize(
//                .padding(16.dp)
//                .clip(RoundedCornerShape(15.dp))
            )
        } else {
            Text("")
        }
    }
}