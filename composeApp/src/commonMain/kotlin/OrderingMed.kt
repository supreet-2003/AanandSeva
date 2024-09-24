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
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
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
    import org.example.anandsevakmp.ImageDisplayScreen
    import org.jetbrains.compose.resources.painterResource
   import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


    suspend fun fetchMedicineOrders(apiClient: ApiClient): List<Order>? {
    return try {
        val response = apiClient.fetchMedicineOrders()
        processOrders(response, driveService)
        println("Response of fetch medicines: $response")
        response
    } catch (e: Exception) {
        println("Error: ${e.message}")
        null
    }
}
    @Composable
    fun MedScreen(navController: NavHostController, viewModel: ImagePickerViewModel) {

        var medicineOrders by remember { mutableStateOf<List<Order>?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val apiClient = remember { ApiClient() }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                medicineOrders = fetchMedicineOrders(apiClient)
            }
        }
        MaterialTheme(
            lightColors(
                background = AppColors.Background
            )
        ) {
            Surface(color = MaterialTheme.colors.background) {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("AanandSeva", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                            navigationIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Home,
                                    contentDescription = "Back",
                                    modifier = Modifier.clickable {
                                        navController.navigate("screen2")
                                    }
                                )
                            },
                            actions = {
                                Icon(
                                    imageVector = Icons.Outlined.ShoppingCart,
                                    contentDescription = "Cart",
                                    modifier = Modifier.padding(end = 16.dp).clickable {
                                        navController.navigate("screen5")
                                    }
                                )
                            },
                            backgroundColor = Color.White,
                            elevation = 4.dp
                        )
                    }, bottomBar = {
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
                                    viewModel.resetImagePicked()
                                    navController.navigate("medpop")
                                },
                            backgroundColor = AppColors.Background
                        ) {
                            Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {
                        Text(
                            "Order Your Medicines",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.Black,
                                shadow = Shadow(
                                    color = Color.Gray,
                                    blurRadius = 3f
                                )
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                            MedList(medicineOrders)
                    }
                }
            }
        }
    }

