import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.anandsevakmp.ImageDisplayScreen


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MedOrder(index:Int,order: Order) {
    val textInput = order.comments[0].text;
     Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Card(
            elevation = 8.dp, modifier = Modifier
                .fillMaxWidth().height(500.dp).padding(8.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

//                val imageUriState = viewModel.imageData.collectAsState()
//                    imageUriState.value?.let { imageUri ->
//                        ImageDisplayScreen(navController = null, imageUri = imageUri.imagePath)
//                    }
                OutlinedTextField(
                    value = textInput, onValueChange = {}, readOnly = true,
                    modifier = Modifier.fillMaxWidth().height(500.dp),
                )
            }
            }
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedList(orderData: List<Order>?){
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    HorizontalPager(state = pagerState) { page ->
        orderData?.get(page)?.let { MedOrder(page, it) }
    }
}
