import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { App(navController) }
        composable("screen2") { HomeScreen(navController) }
        composable("screen3") { LabTest(navController) }
        composable(
            "screen4/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            UserDetails(navController,backStackEntry.arguments?.getString("id")!!)
        }
        composable("screen5") {MedScreen(navController)}
//        composable("screen6") {MedPop(navController)}
        composable("screen7") {OtpScreen(navController)}
//        composable("screen7",
//            arguments = listOf(navArgument("mob") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val mob = backStackEntry.arguments?.getString("mob") ?: ""
//            OtpScreen(navController=navController,mob = mob)
//        }
    }
    }
