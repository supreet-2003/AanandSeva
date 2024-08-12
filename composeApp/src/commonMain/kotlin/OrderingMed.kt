import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.medicine
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource

@Composable
fun MedScreen(navController: NavHostController) {
    MaterialTheme(
        lightColors(
            background = AppColors.Background

        )
    ) {
        var showDialog by remember { mutableStateOf(false) }

        Surface(
            color = MaterialTheme.colors.background
        ) {
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
                                                navController.navigate("screen5 ")

                                            }
                                        )
                                )

                            }

                            Text(
                                "Order Your Medicines",
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(50.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).clickable(onClick = {
                              navController.navigate("screen2")
                            }),
                            tint = Color.Gray
                        )
                        Image(
                            painterResource(Res.drawable.medicine), contentDescription = null,
                            modifier = Modifier.size(40.dp).clickable(onClick = { })
                        )
                        Image(
                            painterResource(Res.drawable.flask),
                            contentDescription = "Lab Test",
                            modifier = Modifier.size(40.dp)
                                .clickable
                                    (
                                    onClick = { navController.navigate("screen3") })
                        )
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp).clickable(onClick = {
                                navController.navigate("screen4")

                            })
                        )


                    }

                },
                floatingActionButton = {

                    FloatingActionButton(

                        onClick = {
                        showDialog=true},
                        backgroundColor = AppColors.Background
                    ) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "Add")}
                }
                ,
                floatingActionButtonPosition = FabPosition.End
            ) { paddingValues->

                if (showDialog){
                    MedPop(onDismiss = {showDialog=false})
                }
                Column (
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
//                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    MedList()
                    }

                    }
                }
            }
        }

