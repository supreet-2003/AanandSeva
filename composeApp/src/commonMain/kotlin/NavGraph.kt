import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.anandsevakmp.ImageDisplayScreen
import org.example.anandsevakmp.ImagePickerScreen

@Composable
fun Navigation(viewModel: ImagePickerViewModel) {
    val navController = rememberNavController()
    val launchScreen: String
    val token: String = settings.getString("auth_token","")
    launchScreen = if(token === "")
        "screen1"
    else
        "screen2"
    NavHost(navController = navController, startDestination = launchScreen) {
        composable("screen1") { App(navController) }
        composable("screen2") { HomeScreen(navController) }
        composable("screen3") { LabTest(navController) }
        composable(
            "screen4/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            UserDetails(navController,backStackEntry.arguments?.getString("id")!!)
        }
            composable("screen5") { MedScreen(navController,viewModel) }
        composable(
            "screen7/{contact}/{otp}",
            arguments = listOf(
                navArgument("contact") { type = NavType.StringType },
                navArgument("otp") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val contact = backStackEntry.arguments?.getString("contact")!!
            val otp = backStackEntry.arguments?.getInt("otp")!!
            OtpScreen(navController, contact, otp)
        }
        composable("medpop") {MedPop(
            navController = navController,
            onDismiss = { navController.popBackStack() },
            onUploadClick = {
                // Handle upload action here if necessary
            },
            onOrderClick = {
                // Handle order action here if necessary
            },
            viewModel = viewModel
        )}
            composable("imagepicker"){
                ImagePickerScreen(navController,viewModel)
            }
            composable("imageDisplay/{imageUri}",  arguments = listOf(navArgument("imageUri") { type = NavType.StringType })){
                    backStackEntry ->
                val imageUri = backStackEntry.arguments?.getString("imageUri")
                ImageDisplayScreen(navController, imageUri)
            }
        }
    }


