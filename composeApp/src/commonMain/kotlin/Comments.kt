import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
 fun Comments(navController: NavController,
              onComment:(String)->Unit,
              onDismiss:()->Unit
 ){
//  var onDissmiss by remember { mutableStateOf{"false"} }
  var text by remember { mutableStateOf("") }

  AlertDialog(onDismissRequest = onDismiss, backgroundColor = Color.White,
   text = {
    Column (
     modifier = Modifier.fillMaxWidth()
    ){
      Text(
       "Add Comments",
       modifier = Modifier.align(Alignment.CenterHorizontally),
       fontWeight = FontWeight.Bold
      )
     OutlinedTextField(
      value = text, onValueChange = {
        newText ->
       text = newText
      },
      modifier = Modifier.fillMaxWidth().height(250.dp).padding(8.dp),
      label = { Text("Comment") }, shape = RoundedCornerShape(15.dp)
     )
  }
}, confirmButton = {
    Button(colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.SoftPurple,contentColor = Color.White)
     , shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(end = 8.dp),
     onClick = {
onComment(text)
      navController.popBackStack()
     },
    ) {
     Text(text = "ORDER")

    }
   })
}