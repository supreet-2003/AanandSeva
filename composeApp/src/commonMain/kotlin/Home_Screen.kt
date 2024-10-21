import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.home_logo
import anandseva_kmp.composeapp.generated.resources.lab_logo
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.medicine
import anandseva_kmp.composeapp.generated.resources.order_logo
import anandseva_kmp.composeapp.generated.resources.profile_logo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.painterResource

// Assuming this function is in a CoroutineScope
suspend fun fetchAllDoctors(apiClient: ApiClient): List<Doctor>? {
    return try {
            val response = apiClient.fetchAllDoctors()

            fetchDoctorImages(response, driveService)

            println("Response: $response")
        loading.value= false
            response
    }
    catch (e: Exception) {
        println("Error: ${e.message}")
        null
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClient() }
    var doctorsData by remember { mutableStateOf<List<Doctor>?>(null) }
    var filteredDoctorsData by remember { mutableStateOf<List<Doctor>?>(null) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            doctorsData = fetchAllDoctors(apiClient)
            println("doctorrrss$doctorsData")
        }
    }

    // Filtered list of doctors based on search text
     filteredDoctorsData = doctorsData?.filter { doctor ->
        doctor.name.contains(searchText.text, ignoreCase = true) ||
                doctor.specialization.contains(searchText.text) ||
                doctor.clinicAddress.contains(searchText.text, ignoreCase = true)
    }

    MaterialTheme(
        colors = lightColors(
            background = AppColors.OffWhite
        )
    ) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp)
                        )
                        Text(
                            text = "AanandSeva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp).padding(vertical = 5.dp)
                        )
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                        value = searchText,
                        onValueChange = { searchText = it },
                        shape = RoundedCornerShape(15.dp),
                        label = { Text("Search for Doctors") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Button"
                            )
                        },
                        trailingIcon = {
                            if (searchText.text.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear Button",
                                    modifier = Modifier.clickable {
                                        searchText = TextFieldValue("")
                                    }
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                val selectedTab = remember { mutableStateOf("home") }


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
        ) {
          if(loading.value){
              LoadingScreen()
          } else if (doctorsData != null) {
              if(doctorsData!!.size === 0) {
                  Box(
                      modifier = Modifier
                          .fillMaxSize(),  // Make the Box fill the entire screen
                      contentAlignment = Alignment.Center  // Center the content inside the Box
                  ) {
                      Text("No Doctors available")
                  }
              } else if(filteredDoctorsData!!.size != 0) {
                  LazyColumn(
                      modifier = Modifier.fillMaxSize(),
                  ) {
                      items(filteredDoctorsData!!) { doctor ->
                          BaseList(doctor)  // Pass each doctor object to BaseList
                      }
                  }
              }
          }
        }
    }


}


@Composable
fun BottomBarIcon1(
    painter: Painter,
    tab: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(1.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(AppColors.SoftPurple, RoundedCornerShape(50))
            )
        }
    }
}