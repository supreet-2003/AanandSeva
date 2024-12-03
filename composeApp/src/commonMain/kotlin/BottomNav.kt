
import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.home_logo
import anandseva_kmp.composeapp.generated.resources.lab_logo
import anandseva_kmp.composeapp.generated.resources.order_logo
import anandseva_kmp.composeapp.generated.resources.profile_logo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNav (navController: NavHostController, activeTab: String){

 MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
                val selectedTab = remember { mutableStateOf(activeTab) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.OffWhite)
                        .padding(8.dp)
                        .padding(horizontal = 8.dp)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    BottomBarIcon1(
                        painterResource(Res.drawable.home_logo),
                        "home",
                        selectedTab.value == "home",
                        onClick = {
                            selectedTab.value = "home"
                            navController.navigate("screen2")
                        }
                    )
                    BottomBarIcon1(
                        painterResource(Res.drawable.order_logo),
                        "orders",
                        selectedTab.value == "orders",
                        onClick = {
                            selectedTab.value = "orders"
                            navController.navigate("screen5/true")
                        }
                    )
                    BottomBarIcon1(
                        painterResource(Res.drawable.lab_logo),
                        "lab",
                        selectedTab.value == "lab",
                        onClick = {
                            selectedTab.value = "lab"
                            navController.navigate("screen3")
                        }
                    )
                   BottomBarIcon1(
                        painterResource(Res.drawable.profile_logo),
                        "profile",
                        selectedTab.value == "profile",
                        onClick = {
                            selectedTab.value = "profile"
                            navController.navigate("logout")
                        }
                    )
                }
            }
        }