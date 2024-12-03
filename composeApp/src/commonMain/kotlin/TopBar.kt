import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource


@Composable
fun TopBar () {

    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
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
    }
}