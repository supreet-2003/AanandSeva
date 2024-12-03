import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.home_logo
import anandseva_kmp.composeapp.generated.resources.lab_logo
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.medicine
import anandseva_kmp.composeapp.generated.resources.order_logo
import anandseva_kmp.composeapp.generated.resources.profile_logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.lightColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LabTest(navController: NavHostController) {
    MaterialTheme(
        colors = lightColors(
            background = AppColors.OffWhite
        )
    ) {
        var showDialog1 by remember { mutableStateOf(false) }
        val searchText = remember { mutableStateOf(TextFieldValue("")) }

        Scaffold(
            topBar = {

                Column(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopBar()
                }
            },
            bottomBar = { BottomNav(navController,"lab") },
            floatingActionButton = {
                if(userType == "User" && loading.value == false){
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate("labtestpop")
                                },
                                backgroundColor = AppColors.Background,
                            ) {
                                Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                            }
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Text(
                "Upload Your Lab Tests",
                modifier = Modifier.padding(8.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 3f
                    )
                ),
                fontSize = 30.sp
            )
            Column(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LabList()
            }
        }
    }
}



