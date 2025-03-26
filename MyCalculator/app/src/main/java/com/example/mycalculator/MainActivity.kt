package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var num1 by remember {
        mutableStateOf("")
    }
    var num2 by remember {
        mutableStateOf("")
    }
    var result by remember {
        mutableStateOf("Result: ")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "My Calculator",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Enter first number") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Enter second number") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CalculatorButton("+") { calculate(num1, num2, '+') { result = it } }
            CalculatorButton("-") { calculate(num1, num2, '-') { result = it } }
            CalculatorButton("×") { calculate(num1, num2, '*') { result = it } }
            CalculatorButton("÷") { calculate(num1, num2, '/') { result = it } }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            CalculatorButton("%") { calculate(num1, num2, '%') { result = it } }
            CalculatorButton("C") {
                num1 = ""
                num2 = ""
                result = "Result: "
            }
        }
    }
}

@Composable
fun CalculatorButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff4081)),
        modifier = Modifier.size(72.dp)
    ) {
        Text(text = symbol, fontSize = 20.sp)
    }
}

fun calculate(num1: String, num2: String, operation: Char, updateResult: (String) -> Unit) {
    val n1 = num1.toDoubleOrNull()
    val n2 = num2.toDoubleOrNull()

    if (n1 == null || n2 == null) {
        updateResult("Invalid input!")
        return
    }

    val res = when (operation) {
        '+' -> n1 + n2
        '-' -> n1 - n2
        '*' -> n1 * n2
        '/' -> if (n2 != 0.0) n1 / n2 else "Cannot divide by zero"
        '%' -> if (n2 != 0.0) n1 % n2 else "Cannot modulo by zero"
        else -> "Unknown operation"
    }
    updateResult("Result: $res")
}
