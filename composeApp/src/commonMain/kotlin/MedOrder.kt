import AppColors.Surface
import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.nophoto
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource

@Composable
fun MedOrder(index:Int) {

                Card(elevation = 8.dp, modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painterResource(Res.drawable.nophoto),
                            contentDescription = "Lab Test",
                            modifier = Modifier.size(90.dp).padding(8.dp)
                        )
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Manufacturing Company",
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Medicine Name",
                                fontSize = 15.sp,
                                style = TextStyle(fontStyle = FontStyle.Italic)
                            )

                            Text(
                                text = "Status",
                                fontSize = 8.sp,
                                style = MaterialTheme.typography.subtitle2
                            )
                            Text(
                                text = "In Stock",
                                fontSize = 15.sp,
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }


                    }
                }
            }




@Composable
fun MedList(){
    LazyColumn(){
        items(10) { index ->
            MedOrder(index)
        }
    }
}