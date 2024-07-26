import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun UserDetails(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bloodgrp by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

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
                Text("Enter Your Details", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(30.dp))
                OutlinedTextField(value = username, onValueChange = {
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
                Button(shape = RoundedCornerShape(8.dp), modifier = Modifier.align(Alignment.CenterHorizontally),onClick = {
                    navController.navigate("screen2")
                }){
                    Text(text="Save Your Details")
                }
            }
        }
    }
}



