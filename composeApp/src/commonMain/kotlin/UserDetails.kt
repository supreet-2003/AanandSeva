import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
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


    suspend fun saveUserDetails(name:String,age:String,address:String,bloodgroup:String,gender:String): Any? {
        try {
            println("Id:$id")
            val json = """{"name": "$name", "type": "User", "bloodgroup": "$bloodgroup", "address": "$address", "age": "$age", "gender": "$gender"}"""
            val response = apiClient.saveUserDetails(json, id)
            println("Response: $response")
            return response
        } catch (e: Exception) {
            println("Error: $e.message")
            return {}
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
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.padding(5.dp))
                Text("Enter Your Details", color = Color(0xFF746AC2),modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(30.dp))
                OutlinedTextField(value = username,
                    colors =TextFieldDefaults.outlinedTextFieldColors(

                    )
                    ,onValueChange = {
                    username = it
                }, label = { Text(text = "Username") }, modifier = Modifier.align(Alignment.CenterHorizontally))
                OutlinedTextField(value = age, onValueChange = {

                    age = it
                }, label = { Text(text = "Age") },modifier = Modifier.align(Alignment.CenterHorizontally))
                OutlinedTextField(value = bloodgrp, onValueChange = {
                    bloodgrp = it
                }, label = { Text(text = "Blood Group") },modifier = Modifier.align(Alignment.CenterHorizontally))
                OutlinedTextField(value = gender, onValueChange = {
                    gender = it
                }, label = { Text(text = "Gender") },modifier = Modifier.align(Alignment.CenterHorizontally))
                OutlinedTextField(value = address, onValueChange = {
                    address = it
                }, label = { Text(text = "Address") },modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.padding(20.dp))
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White),shape = RoundedCornerShape(8.dp), modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    ,onClick = {
                        coroutineScope.launch {
                            val result = saveUserDetails(username,age,address,bloodgrp,gender)
                            println("Result: $result")
                            if(result.toString() !== null)
                             navController.navigate("screen2")
                        }
                }){
                    Text(text="Save Your Details")
                }
            }
        }
    }
}



