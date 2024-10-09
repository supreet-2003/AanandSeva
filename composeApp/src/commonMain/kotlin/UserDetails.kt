import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.lightColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun UserDetails(navController: NavHostController,id: String) {
    var username by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bloodgrp by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClient() }
    var isFormValid by remember { mutableStateOf(false) }
    val isUsernameValid = username.length >= 3
    var genderExpanded by remember { mutableStateOf(false) }
    var bloodExpanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val addressLines = remember { List(4) { "" } }
    var addressValues by remember { mutableStateOf(addressLines) }
    val scrollState = rememberScrollState()
    val addressLabels = listOf("Flat/House no", "Apartment/Building", "City", "State", "Pincode")

    LaunchedEffect(username, age, bloodgrp, gender, address) {
        isFormValid = username.isNotBlank() && age.isNotBlank() && bloodgrp.isNotBlank() && gender.isNotBlank() && address.isNotBlank()
    }


    suspend fun saveUserDetails(name:String,age:String,address:String,bloodgroup:String,gender:String): User? {
        try {
            println("Id:$id")
            val json = """{"name": "$name", "type": "User", "bloodgroup": "$bloodgroup", "address": "$address", "age": "$age", "gender": "$gender"}"""
            val response = apiClient.saveUserDetails(json, id)
            println("Response: $response")
            if(response != null && response.name != null && response.type != null){
                storeCache("userName",response.name)
                storeCache("userType",response.type)
            }
            return response
        } catch (e: Exception) {
            println("Error: $e.message")
            return null
        }
    }

    MaterialTheme(
        colors = lightColors(
            background = AppColors.Background
        )
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            )
            {
                Text("Enter Your Details", color = Color(0xFF746AC2),modifier = Modifier.padding(20.dp).align(Alignment.CenterHorizontally))

                //Username
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Full Name") },
                    isError = isFocused&&!isUsernameValid ,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.8f)
                        .onFocusChanged {
                            focusState ->
                            isFocused=focusState.isFocused

                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = if (!isUsernameValid&&isFocused) Color.Red else Color.Gray
                    )
                )
                if (!isUsernameValid&&isFocused) {
                    Text(
                        "Name must be at least 3 characters",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }


                OutlinedTextField(value = age, onValueChange = {

                    age = it
                }, label = { Text(text = "Age") },modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.8f))





                //Gender
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = { genderExpanded = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = null,
                        modifier = Modifier.fillMaxWidth(0.8f).border(1.dp, Color.Gray)

                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (gender.isNotEmpty()) gender else "Select Gender",
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
                        expanded =genderExpanded,
                        onDismissRequest = { genderExpanded = false }
                    ) {
                        val genders = listOf("Male", "Female", "Other")
                        genders.forEach { selectedGender ->
                            DropdownMenuItem(
                                onClick = {
                                    gender = selectedGender
                                    genderExpanded = false
                                },
                                text = { Text(selectedGender) }
                            )
                        }
                    }
                }

                //BloodGroup

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = { bloodExpanded = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = null,
                        modifier = Modifier.fillMaxWidth(0.8f).border(1.dp, Color.Gray),

                        ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
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
                        val bloodGroup = listOf("A+","A-","B+","B-","AB+","AB-","O+","O-")
                        bloodGroup.forEach { selectedgroup ->
                            DropdownMenuItem(
                                onClick = {
                                    bloodgrp = selectedgroup
                                    bloodExpanded= false
                                },
                                text = { Text(selectedgroup) }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.Transparent)
                ) {
                    Column() {
                        for (i in addressLabels.indices) {
                            TextField(
                                value = addressValues.getOrElse(i) { "" },
                                onValueChange = { newValue ->
                                    addressValues = addressValues.toMutableList().apply {
                                        if (i < size) this[i] = newValue
                                        else add(newValue)
                                    }
                                },
                                label = { Text(addressLabels[i]) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Gray,
                                    unfocusedIndicatorColor = Color.Gray
                                )
                            )
                        }
                    }
                }

                Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White),shape = RoundedCornerShape(8.dp), modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    ,onClick = {
                        coroutineScope.launch {
                            val result = saveUserDetails(username,age,address,bloodgrp,gender)
                            println("Result: $result")
                            if(result !== null){
                                navController.navigate("screen2")
                            }
                        }
                }){
                    Text(text="Save Your Details")
                }
            }
        }
    }
}



