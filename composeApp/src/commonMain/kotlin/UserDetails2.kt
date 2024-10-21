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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.lightColors
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UserDetails2(navController: NavHostController) {
    var mob by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClient() }
    var isFormValid by remember { mutableStateOf(false) }
    val isnameValid = firstname.length >= 3
    val is2nameValid = lastname.length >= 3
    var genderExpanded by remember { mutableStateOf(false) }
    var bloodExpanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val addressLines = remember { List(4) { "" } }
    var addressValues by remember { mutableStateOf(addressLines) }
    var gender by remember { mutableStateOf("") }
    var  age by remember { mutableStateOf("") }

    var  bloodgrp by remember { mutableStateOf("") }





    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(modifier = Modifier.align(Alignment.Start)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp).clickable(onClick ={ navController.navigate("screen2")}
                    )
                    )
                }

                androidx.compose.material.Text(
                    "Step 1 of 2",
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
                    "Enter Your First Name, Last Name, Age, Gender, Blood Group.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 20.dp).padding(5.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    //Name
                    OutlinedTextField(
                        value = firstname,
                        onValueChange = { firstname = it },
                        label = { androidx.compose.material.Text(text = "First Name") },
                        isError = isFocused && !isnameValid,
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.94f)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused

                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = if (!isnameValid && isFocused) Color.Red else Color.Gray
                        )
                    )
                    if (!isnameValid && isFocused) {
                        androidx.compose.material.Text(
                            "Name must be at least 3 characters",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    OutlinedTextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        label = { androidx.compose.material.Text(text = "Last Name") },
                        isError = isFocused && !is2nameValid,
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.94f)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused

                            },
                        shape = RoundedCornerShape(8.dp),

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = if (!is2nameValid && isFocused) Color.Red else Color.Gray
                        )
                    )
                    if (!is2nameValid && isFocused) {
                        androidx.compose.material.Text(
                            "Name must be at least 3 characters",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    OutlinedTextField(
                        value = age,
                        onValueChange = {
                            age = it
                        },
                        label = { androidx.compose.material.Text(text = "Age") },
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.94f),
                        shape = RoundedCornerShape(8.dp)

                    )

                    //Gender
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(8.dp),
                    ) {
                        Button(
                            onClick = { genderExpanded = true },
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
                                    text = if (gender.isNotEmpty()) gender else "Gender",
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
                            expanded = genderExpanded,
                            onDismissRequest = { genderExpanded = false }
                        ) {
                            val genders = listOf("Male", "Female", "Other")
                            genders.forEach { selectedGender ->
                                DropdownMenuItem(
                                    onClick = {
                                        gender = selectedGender
                                        genderExpanded = false
                                    },
                                    text = { androidx.compose.material.Text(selectedGender) }
                                )
                            }
                        }
                    }

                    //Blood Group
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(8.dp)
                    ) {
                        Button(
                            onClick = { bloodExpanded = true },
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
                                    text = if (bloodgrp.isNotEmpty()) bloodgrp else "Blood Group",
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
                            expanded = bloodExpanded,
                            onDismissRequest = { bloodExpanded = false }
                        ) {
                            val bloodGroup =
                                listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
                            bloodGroup.forEach { selectedgroup ->
                                DropdownMenuItem(
                                    onClick = {
                                        bloodgrp = selectedgroup
                                        bloodExpanded = false
                                    },
                                    text = { androidx.compose.material.Text(selectedgroup) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.SoftPurple,
                    contentColor = Color.White
                ), shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally).fillMaxWidth(0.94f).padding(8.dp),

                    onClick = {
                        navController.navigate("addressdetail")

                    }
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