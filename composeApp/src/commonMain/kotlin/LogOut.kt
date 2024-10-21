import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController

@Composable
fun LogOutScreen (navController: NavHostController){
    LaunchedEffect(Unit) {
        userName = ""
        userType = ""
        id = "1"
        token = ""
        navController.navigate("screen1")
    }

    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {}


}