package com.example.secondproject

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {
    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF0F2F5)) {
                StudentFormScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen() {
    val context = LocalContext.current
    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context, { _, y, m, d -> dateState = "$d/${m + 1}/$y" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val customColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF6C63FF),
        unfocusedBorderColor = Color.Transparent,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.White,
        disabledBorderColor = Color.Transparent,
        disabledTextColor = Color.Black
    )

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Registration", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6C63FF))

        OutlinedTextField(value = nameState, onValueChange = { nameState = it }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = customColors)
        OutlinedTextField(value = surnameState, onValueChange = { surnameState = it }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = customColors)
        OutlinedTextField(value = emailState, onValueChange = { emailState = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = customColors)

        OutlinedTextField(
            value = dateState, onValueChange = {}, label = { Text("Birth Date") },
            modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() },
            enabled = false, shape = RoundedCornerShape(24.dp), colors = customColors
        )

        Text("Choose Path:", modifier = Modifier.align(Alignment.Start))
        listOf("Android", "iOS", "Web").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clickable { selectedOption = option }) {
                RadioButton(selected = (option == selectedOption), onClick = { selectedOption = option }, colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6C63FF)))
                Text(option)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Switch(checked = isAgreed, onCheckedChange = { isAgreed = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF6C63FF)))
            Spacer(modifier = Modifier.width(10.dp))
            Text("I agree to terms")
        }

        Button(
            onClick = {
                if (nameState.isNotBlank() && surnameState.isNotBlank() && emailState.isNotBlank() && dateState.isNotBlank() && selectedOption.isNotBlank() && isAgreed) {
                    Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
        ) {
            Text("SUBMIT", color = Color.White)
        }
    }
}