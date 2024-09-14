import androidx.compose.material.Text
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

        NavHost(navController = navController, startDestination = "screen1") {
            composable("screen1") { App(navController) }
            composable("screen2") { HomeScreen(navController) }
            composable("screen3") { LabTest(navController) }
            composable("screen4") { UserDetails(navController) }
            composable("screen5") { MedScreen(navController,viewModel) }
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
            composable("screen7/{data}") {
                backStackEntry ->
                val data = backStackEntry.arguments?.getString("data")
                OtpScreen(navController,data)

            }
            composable("imagepicker"){
                ImagePickerScreen(navController,viewModel)
            }
            composable("imageDisplay/{imageUri}",  arguments = listOf(navArgument("imageUri") { type = NavType.StringType })){
                    backStackEntry ->
                val imageUri = backStackEntry.arguments?.getString("imageUri")
                ImageDisplayScreen(navController, imageUri)
            }
            composable("screen6") {
                Comments(
                    navController = navController,
                    onComment = { comment ->
                        viewModel.updateTextInput(comment)
                    }// Update the comment in ViewModel
                   ,
                    onDismiss = {navController.popBackStack()}
                )

            }

//        }
        }
    }


