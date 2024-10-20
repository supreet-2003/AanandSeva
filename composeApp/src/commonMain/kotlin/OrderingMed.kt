    import anandseva_kmp.composeapp.generated.resources.Res
    import anandseva_kmp.composeapp.generated.resources.flask
    import anandseva_kmp.composeapp.generated.resources.home_logo
    import anandseva_kmp.composeapp.generated.resources.lab_logo
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
    import androidx.compose.ui.graphics.painter.Painter
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
           
                response = apiClient.fetchMedicineOrders()
                processOrders(response, driveService)
            
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
                   
                        loading.value = true
                        _medicineOrders.value = fetchMedicineOrders(apiClient)
                    
                    loading.value = false
                }
            }
        }
        MaterialTheme(
            lightColors(
                background = AppColors.OffWhite
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
                        val selectedTab = remember { mutableStateOf("orders") }

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
                                    navController.navigate("userdetail")
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        if(userType == "User" && loading.value == false){
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

