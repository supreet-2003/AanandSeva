import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.example.anandsevakmp.ImageDisplayScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MedOrder(index:Int,viewModel: ImagePickerViewModel,navController: NavController) {
    val textInput by viewModel.textInput.collectAsState()
    val shouldDisplayImage by viewModel.shouldDisplayImage.collectAsState()
    var selectedOption by remember { mutableStateOf("Status") }
    val selectedStatus by viewModel.selectedStatus.collectAsState()

    var dropdown by remember { mutableStateOf(false) }
    var options = listOf("Ordered", "Processing", "Cancelled", "Completed")
//    val imageUriState = viewModel.imageUri.collectAsState()
//                ImageDisplayScreen(navController = null, imageUri = imageUriState.value)
    var commentText by remember { mutableStateOf("") }
    var commentList by remember { mutableStateOf(listOf<String>()) }

    if (textInput.isNotBlank() && !commentList.contains(textInput)) {
        commentList = commentList + textInput
    }

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
//                        modifier = Modifier.align(Alignment.End),
                    onClick = { dropdown = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = when (selectedStatus) {
                            "Cancelled" -> Color(0xFFC7253E)
                            "Completed" -> Color(0xFF00712D)
//                            "Ordered" -> Color.Green
                            else -> AppColors.SoftPurple
                        },
                        contentColor = Color.White

                    ),
                    shape = RoundedCornerShape(10.dp),
//                        modifier= Modifier.size(50.dp).width(20.dp)
                ) {
                    Text(text = selectedStatus)
                }

                DropdownMenu(
//                        modifier = Modifier.size(50.dp),
                    modifier = Modifier
//                            .size(50.dp)
                        .width(150.dp)
//                            .clickable { dropdown = true }
                    ,
                    expanded = dropdown,
                    onDismissRequest = { dropdown = false }

                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updateStatus(option)
                                selectedOption = option
                                dropdown = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
//                }
//                }


                val imageUriState = viewModel.imageUri.collectAsState()
//                if(shouldDisplayImage) {
                imageUriState.value?.let { imageUri ->
                    ImageDisplayScreen(navController = null, imageUri = imageUri)
                }
            }
//                }
                Column {
                    //Comment Bar
                    OutlinedTextField(
                        value = commentList.joinToString(separator = "\n"),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().heightIn(min=10.dp, max=200.dp),
                        shape = RoundedCornerShape(6.dp)
                    )


                    //Comment Adding Bar
                    val searchText = remember { mutableStateOf(TextFieldValue("")) }
                    androidx.compose.material.OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
//                        .weight(1f)
//                            .shadow(10.dp)
//                            .size(50.dp),
                        ,
                        value = searchText.value,
                        onValueChange = {
                                newValue ->
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
                                        commentList = commentList + searchText.value.text
                                        searchText.value = TextFieldValue("") // Clear the input field after sending
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
fun MedList(viewModel: ImagePickerViewModel){
    val navController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    HorizontalPager(state = pagerState) { page ->
        MedOrder(page, viewModel, navController = navController)

    }
    }
