import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.home
import anandseva_kmp.composeapp.generated.resources.logo
import anandseva_kmp.composeapp.generated.resources.patient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController



@Composable

fun HomeScreen(navController: NavHostController) {
        MaterialTheme(
        colors = lightColors(
            background = AppColors.SoftBlue
        )
    ) {

        Surface(
            color = MaterialTheme.colors.background
        ) {
            val searchText = remember { mutableStateOf(TextFieldValue("")) }


            Column {

                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                    Image(painterResource(Res.drawable.logo), contentDescription = null,modifier = Modifier.padding(10.dp))
                    Text(
                        text = "AanandSeva",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(5.dp).align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(50.dp).padding(vertical = 5.dp))
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(10.dp)
                        .size(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                    ,
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Search for Medicines") },

                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Button")
                    },
                    trailingIcon = {
                        if (searchText.value.text.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Button",
                                modifier = Modifier.clickable {
                                    searchText.value = TextFieldValue("")
                                }

                            )
                        }
                    },

                    )



                Spacer(modifier = Modifier.weight(1f))


                //  Bottom Icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        tint = Color.Gray, modifier = Modifier.size(40.dp).clickable(onClick = {

                        })
                    )
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = Color.Gray, modifier = Modifier.size(40.dp).clickable(onClick = {})
                    )
                    Image(
                        painterResource(Res.drawable.flask),
                        contentDescription = "Lab Test",
                        modifier = Modifier.size(40.dp)
                            .clickable(
                                onClick = {navController.navigate("screen2")})
                    )
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.Gray, modifier = Modifier.size(40.dp).clickable(onClick = {})
                    )





                }


            }
        }
    }
}






