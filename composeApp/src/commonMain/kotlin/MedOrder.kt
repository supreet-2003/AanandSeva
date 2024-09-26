import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.example.anandsevakmp.ImageDisplayScreen
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

suspend fun updateOrderStatus(apiClient: ApiClient, order: Order, value: String): Any? {
    return try {
        var response = apiClient.updateOrder(order._id , order.orderStatus,"orderStatus")
        response
    } catch (e: Exception) {
        println("Error: ${e.message}")
        null
    }
}

suspend fun addOrderComment(apiClient: ApiClient, order: Order, value: comments): Any? {
    return try {
        var response = apiClient.updateOrder(order._id , "${value.text}+${value.commentedBy}","comments")
        response
    } catch (e: Exception) {
        println("Error: ${e.message}")
        null
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MedOrder(index:Int,order: Order,navController: NavController) {
    var selectedOption by remember { mutableStateOf("Status") }
    var selectedStatus by remember { mutableStateOf(order.orderStatus) }

    var dropdown by remember { mutableStateOf(false) }
    val options = listOf("Ordered", "Processing", "Cancelled", "Completed")
    var commentList by remember { mutableStateOf(listOf(*order.comments)) }
    val apiClient = remember { ApiClient() }
    val coroutineScope = rememberCoroutineScope()

     Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Card(
            elevation = 8.dp, modifier = Modifier
                .fillMaxWidth().fillMaxHeight().padding(8.dp)
        ) {

            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
                ) {
                Column{
                Button(
                    onClick = {
                        if (userType == "Compounder") {
                            dropdown = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = when (selectedStatus) {
                            "Cancelled" -> Color(0xFFC7253E)
                            "Completed" -> Color(0xFF00712D)
                            else -> AppColors.SoftPurple
                        },
                        contentColor = Color.White

                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = selectedStatus)
                }

                DropdownMenu(
                    modifier = Modifier
                        .width(150.dp),
                    expanded = dropdown,
                    onDismissRequest = { dropdown = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                order.orderStatus = option
                                selectedStatus = option
                                selectedOption = option
                                coroutineScope.launch { updateOrderStatus(apiClient,order,"status") }
                                dropdown = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
                if(userType == "Compounder") {
                    Text("Ordered By: ${order.orderedBy}")
                }
            }
                ImageDisplayScreen(navController = null, imageUri = order.imageStorageLink)
                Column {
                    //Comment Bar
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        commentList.forEach { comment ->
                            Text(text = "${comment.commentedBy}: ${comment.text}")
                            Spacer(modifier = Modifier.height(8.dp))  // Space between comments
                        }
                    }

                    //Comment Adding Bar
                    val searchText = remember { mutableStateOf(TextFieldValue("")) }
                    androidx.compose.material.OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
                        value = searchText.value,
                        onValueChange = { newValue ->
                            searchText.value = newValue
                        },
                        shape = RoundedCornerShape(15.dp),
                        label = { Text("Send Comments") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Button",
                                modifier = Modifier.clickable {
                                    if (searchText.value.text.isNotBlank()) {
                                        // Update the list of comments with the new comment
                                        val commentData = comments(
                                            text = searchText.value.text,
                                            date = "",
                                            commentedBy = userName
                                        )
                                        order.comments = order.comments.plusElement(commentData)
                                        commentList = commentList.plusElement(commentData)
                                        coroutineScope.launch { addOrderComment(apiClient,order,commentData) }
                                        searchText.value =
                                            TextFieldValue("") // Clear the input field after sending
                                    }
                                }
                            )
                        },

                        )
                }
            }
            }
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedList(orderData: List<Order>?){
    if(loading.value || refreshData.value == "false"){
       LoadingScreen()
    } else if (orderData != null) {
        if(orderData.size === 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),  // Make the Box fill the entire screen
                contentAlignment = Alignment.Center  // Center the content inside the Box
            ) {
                Text("No Past Orders. \nClick on the 'Add' button to order")
            }
        } else {
            val navController = rememberNavController()
            val pagerState = rememberPagerState(pageCount = {
                orderData?.size ?: 0
            })
            HorizontalPager(state = pagerState) { page ->
                orderData?.get(page)?.let { MedOrder(page, it,  navController = navController) }
            }
        }
    }
}
