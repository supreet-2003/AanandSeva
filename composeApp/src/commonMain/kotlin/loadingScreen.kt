import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),   // Makes the Box fill the entire screen
        contentAlignment = Alignment.Center  // Centers the content inside the Box
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary, // You can use the theme's primary color
            strokeWidth = 4.dp                        // Thickness of the indicator
        )
    }
}
