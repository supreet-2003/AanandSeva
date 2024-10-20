import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.lightColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddressDetails(navController: NavHostController) {
    val addressLabels =
        mutableListOf("Flat/House no", "Apartment/Building", "City", "State", "Pincode")
    var flat by remember { mutableStateOf("") }
    var apartment by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    var stateExpanded by remember { mutableStateOf(false) }
    var pincode by remember { mutableStateOf("") }
    val states = listOf("Andhra Pradesh", "Bihar", "Delhi", "Goa", "Gujarat", "Karnataka", "Maharashtra", "Punjab", "Rajasthan", "Tamil Nadu", "Uttar Pradesh", "West Bengal")
    var expanded by remember { mutableStateOf(false) }


    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {

            Column (   modifier = Modifier.fillMaxSize().padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                Row(modifier = Modifier.align(Alignment.Start)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp).clickable(onClick ={ navController.navigate("userdetail")}
                        ),
                    )
                }

                androidx.compose.material.Text(
                    "Step 2 of 2",
                    color = Color.DarkGray,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Start).padding(5.dp)
                )
                androidx.compose.material.Text(
                    "Tell Us about Yourself",
                    style = MaterialTheme.typography.h4,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.Start).padding(5.dp)
                )
                androidx.compose.material.Text(
                    "Enter Your Address where you would like the medicines to deliver and book appointments.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 20.dp).padding(5.dp)
                )


                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {

                        OutlinedTextField(
                            value = flat,
                            onValueChange = { flat = it },
                            label = { androidx.compose.material.Text(text = addressLabels[0]) },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(0.94f),
                            shape = RoundedCornerShape(8.dp)

                        )

                        OutlinedTextField(
                            value = apartment,
                            onValueChange = { apartment = it },
                            label = { androidx.compose.material.Text(text = addressLabels[1]) },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(0.94f),
                            shape = RoundedCornerShape(8.dp)

                        )

                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { androidx.compose.material.Text(text = addressLabels[2]) },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(0.94f),
                            shape = RoundedCornerShape(8.dp)

                        )
                        Row(modifier = Modifier.fillMaxWidth(0.99f)) {

//                            OutlinedTextField(
//                                value = state,
//                                onValueChange = { state = it },
//                                label = { androidx.compose.material.Text(text = addressLabels[3]) },
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(8.dp)
//
//                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .padding(8.dp),
                            ) {
                                Button(
                                    onClick = { stateExpanded = true },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                    elevation = null,
                                    modifier = Modifier.fillMaxWidth(0.98f)
                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                    shape = RoundedCornerShape(8.dp),


                                    ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        androidx.compose.material.Text(
                                            text = if (state.isNotEmpty()) state else "State",
                                            color = Color.Black,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = stateExpanded,
                                    onDismissRequest = { stateExpanded = false }
                                ) {
//                                    val genders = listOf("Male", "Female", "Other")
                                    states.forEach { selectedGender ->
                                        DropdownMenuItem(
                                            onClick = {
                                                state = selectedGender
                                                stateExpanded = false
                                            },
                                            text = { androidx.compose.material.Text(selectedGender) }
                                        )
                                    }
                                }
                            }

//                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedTextField(
                                value = pincode,
                                onValueChange = {
                                    if (it.all { char -> char.isDigit() }) {
                                        pincode = it
                                    }
                                },
                                label = { androidx.compose.material.Text(text = addressLabels[4]) },
                                modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)

                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColors.SoftPurple,
                            contentColor = Color.White
                        ), shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally).fillMaxWidth(0.94f).padding(8.dp),

                            onClick = {}
                        ) {
                            Text( text = "Next",
                                color = Color.White,
                                style = androidx.compose.material.MaterialTheme.typography.button.copy(
                                    lineHeight = 24.sp,
                                    fontSize = 18.sp
                                ))
                        }
                    }
                }
            }

    }
}