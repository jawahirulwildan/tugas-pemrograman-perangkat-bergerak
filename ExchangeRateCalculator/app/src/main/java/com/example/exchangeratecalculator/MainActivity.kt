package com.example.exchangeratecalculator

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
            MultiCurrencyConverter()
//            SimpleCurrencyConverter()
        }
    }
}

@Composable
fun MultiCurrencyConverter() {
    val exchangeRates = mapOf(
        "IDR" to mapOf("USD" to 1 / 16770.0, "MYR" to 1 / 3570.0, "GBP" to 1 / 21100.0, "TWD" to 1 / 540.0),
        "USD" to mapOf("IDR" to 16770.0, "MYR" to 4.67, "GBP" to 0.79, "TWD" to 32.0),
        "MYR" to mapOf("IDR" to 3570.0, "USD" to 0.21, "GBP" to 0.17, "TWD" to 6.9),
        "GBP" to mapOf("IDR" to 21100.0, "USD" to 1.27, "MYR" to 5.88, "TWD" to 40.5),
        "TWD" to mapOf("IDR" to 540.0, "USD" to 0.031, "MYR" to 0.145, "GBP" to 0.025)
    )

    val currencies = exchangeRates.keys.toList()
    var fromCurrency by remember { mutableStateOf("IDR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var amount by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kalkulator Konversi",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Mata Uang",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Konversi $fromCurrency ke $toCurrency",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color(0xFFffab40), RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah dalam $fromCurrency") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            CurrencyDropdown(currencies, fromCurrency) { fromCurrency = it }
            Text(" → ", fontSize = 24.sp, color = Color.White)
            CurrencyDropdown(currencies, toCurrency) { toCurrency = it }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
            val amt = amount.toDoubleOrNull()
            if (amt != null && exchangeRates[fromCurrency]?.get(toCurrency) != null) {
                val rate = exchangeRates[fromCurrency]!![toCurrency]!!
                val converted = amt * rate
                result = "%.2f $fromCurrency = %.2f $toCurrency".format(amt, converted)
            } else {
                result = "Invalid input or conversion rate"
            }
        },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
        ) {
            Text("Konversi", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
    }
}

@Composable
fun CurrencyDropdown(options: List<String>, selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff4081))
        ) {
            Text(selected)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { currency ->
                DropdownMenuItem(text = { Text(currency) }, onClick = {
                    onSelected(currency)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun SimpleCurrencyConverter() {
    val exchangeRate = 16770.0
    var amount by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isIdrToUsd by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kalkulator Konversi",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Mata Uang",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isIdrToUsd) "Konversi IDR ke USD" else "Konversi USD ke IDR",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(Color(0xFFffab40), RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah Uang dalam ${if (isIdrToUsd) "IDR" else "USD"}") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { isIdrToUsd = true },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isIdrToUsd) Color(0xFFff4081) else Color.LightGray
                )
            ) {
                Text("IDR → USD")
            }
            Button(
                onClick = { isIdrToUsd = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isIdrToUsd) Color(0xFFff4081) else Color.LightGray
                )
            ) {
                Text("USD → IDR")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val amt = amount.toDoubleOrNull()
                if (amt != null) {
                    result = if (isIdrToUsd) {
                        val converted = amt / exchangeRate
                        "%.2f IDR = %.2f USD".format(amt, converted)
                    } else {
                        val converted = amt * exchangeRate
                        "%.2f USD = %.2f IDR".format(amt, converted)
                    }
                } else {
                    result = "Invalid amount"
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
        ) {
            Text("Konversi", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
    }
}