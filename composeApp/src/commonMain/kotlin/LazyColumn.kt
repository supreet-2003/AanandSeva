import anandseva_kmp.composeapp.generated.resources.Res
import anandseva_kmp.composeapp.generated.resources.filter
import anandseva_kmp.composeapp.generated.resources.nophoto
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

data class Doctor(
    val _id: String,
    val name: String,
    val clinicAddress: String,
    val contactNumber: String,
    val specialization: List<String>,
    val fee: String,
    val experience: Int
)

@Composable
fun DoctorListScreen(doctors: List<Doctor>) {
}

@Composable
fun BaseList(doctor: Doctor) {
    Card(elevation = 8.dp, modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painterResource(Res.drawable.nophoto),
                contentDescription = "Doctor Photo",
                modifier = Modifier.size(90.dp).padding(8.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = doctor.name, style = MaterialTheme.typography.h5, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Row {
                    Text(
                        text = doctor.specialization.joinToString(", "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = " | ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Thin
                    )
                    Text(
                        text = "${doctor.experience} years experience",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Text(text = doctor.clinicAddress, fontSize = 12.sp, style = MaterialTheme.typography.subtitle2)
                Text(text = "Fee: Rs.${doctor.fee}", fontSize = 11.sp, style = MaterialTheme.typography.subtitle2)
                Text(text = "Contact: ${doctor.contactNumber}", fontSize = 12.sp, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}
