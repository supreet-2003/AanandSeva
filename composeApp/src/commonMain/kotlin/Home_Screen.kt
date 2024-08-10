import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.medicine
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

// Assuming this function is in a CoroutineScope
suspend fun fetchAllDoctors(apiClient: ApiClient): List<Doctor>? {
    return try {
        val response = apiClient.fetchAllDoctors()
        println("Response: $response")
        response
    } catch (e: Exception) {
        println("Error: ${e.message}")
        null
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClient() }
    var doctorsData by remember { mutableStateOf<List<Doctor>?>(null) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            doctorsData = fetchAllDoctors(apiClient)
        }
    }

    MaterialTheme(
        colors = lightColors(
            background = Color.White
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
                            // Handle navigation or state change
                        }),
                        tint = Color.Gray
                    )
                    Image(
                        painter = painterResource(Res.drawable.medicine),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            navController.navigate("screen5")
                        })
                    )
                    Image(
                        painter = painterResource(Res.drawable.flask),
                        contentDescription = "Lab Test",
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            navController.navigate("screen3")
                        })
                    )
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable(onClick = {
                            // Handle navigation or state change
                        }),
                        tint = Color.Gray
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn {
                items(doctorsData ?: emptyList()) { doctor ->
                    BaseList(doctor = doctor)
                }
            }
        }
    }
}
