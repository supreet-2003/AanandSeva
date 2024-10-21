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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(
                            painterResource(Res.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.padding(
                                top = 5.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        )
                        Text(
                            text = "AanandSeva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(5.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp).padding(vertical = 5.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate("screen5/true")

                                    }
                                )
                        )

                    }

                    Text(
                        "Upload Your Lab Tests",
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
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
                }
            },
            bottomBar = {
                val selectedTab = remember { mutableStateOf("lab") }
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

            },
            floatingActionButton = {

                FloatingActionButton(

                    onClick = {
//                            if (showDialog1) {
//                                LabPop(onDismiss = {showDialog1=false})
//                            }
                         showDialog1 = true
                    },
                    backgroundColor = AppColors.Background
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            if (showDialog1) {
                LabPop(onDismiss = { showDialog1 = false },
                    onUploadClick = {
                        navController.navigate("imagepicker")
                        showDialog1 = false // Optionally close the dialog
                    })
            }
            Column(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                LabList()
            }

        }
    }
}



