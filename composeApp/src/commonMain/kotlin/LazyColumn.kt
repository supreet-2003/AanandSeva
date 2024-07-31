import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.flask
import anandseva_kmp.composeapp.generated.resources.nophoto
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun BaseList(index:Int){

    Card(elevation = 8.dp, modifier = Modifier.padding(8.dp).fillMaxWidth(  ) ) {
        Row (
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
        ){
            Image(
                painterResource(Res.drawable.nophoto),
                contentDescription = "Lab Test",
                modifier = Modifier.size(90.dp).padding(8.dp)
            )
            Column (modifier = Modifier.padding(8.dp)){
                Text(text = "Dr. John", style = MaterialTheme.typography.h5, fontWeight = FontWeight.ExtraBold,fontSize = 20.sp)
                Row() {
                    Text(
                        text = "Surgeon",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = " | ",
                        fontSize = 10.sp,

                        fontWeight = FontWeight.Thin)
                    Text(
                        text = "Experience",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold  ,modifier = Modifier.align(Alignment.CenterVertically)                  )
                }
                Text(text = "Address", fontSize = 8.sp,style = MaterialTheme.typography.subtitle2)
                Text(text = "Fees", fontSize = 15.sp,style = MaterialTheme.typography.subtitle2,fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}








