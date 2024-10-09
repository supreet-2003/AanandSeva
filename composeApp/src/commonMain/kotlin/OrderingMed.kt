    import anandseva_kmp.composeapp.generated.resources.Res
    import anandseva_kmp.composeapp.generated.resources.flask
    import anandseva_kmp.composeapp.generated.resources.medicine
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.material.FabPosition
    import androidx.compose.material.FloatingActionButton
    import androidx.compose.material.Icon
    import androidx.compose.material.MaterialTheme
    import androidx.compose.material.Scaffold
    import androidx.compose.material.Surface
    import androidx.compose.material.Text
    import androidx.compose.material.TopAppBar
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.AddCircle
    import androidx.compose.material.icons.outlined.Home
    import androidx.compose.material.icons.outlined.Person
    import androidx.compose.material.icons.outlined.ShoppingCart
    import androidx.compose.material.lightColors
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.Shadow
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavHostController
    import org.jetbrains.compose.resources.painterResource
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.runBlocking

    suspend fun fetchMedicineOrders(apiClient: ApiClient): List<Order>? {
        return try {
            var response : List<Order>? = listOf()
            runBlocking {
                response = apiClient.fetchMedicineOrders()
                processOrders(response, driveService)
            }
            println("resss$response")
            response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    val _medicineOrders = mutableStateOf<List<Order>?>(emptyList())
    val loading = mutableStateOf<Boolean>(value = true)
    val refreshData = mutableStateOf<String>(value = "false")

    @Composable
    fun MedScreen(navController: NavHostController, fetchData: String) {
        val coroutineScope = rememberCoroutineScope()
        val apiClient = remember { ApiClient() }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                refreshData.value = fetchData
                if(fetchData !== "false"){
                    runBlocking {
                        loading.value = true
                        _medicineOrders.value = fetchMedicineOrders(apiClient)
                    }
                    loading.value = false
                }
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
                                        navController.navigate("screen5/true")
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
                        if(userType != "Compounder" && loading.value == false){
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate("medpop")
                                },
                                backgroundColor = AppColors.Background,
                            ) {
                                Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(bottom = 70.dp)
                            .padding(8.dp)
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
                            modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if(fetchData !== "false") {
                            MedList(_medicineOrders.value)
                        }
                    }
                }
            }
        }
    }

