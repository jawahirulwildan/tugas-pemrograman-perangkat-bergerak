package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyBirthdayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Color(0xFFE3F2FD)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        GreetingText(
                            message = "🎉 Happy Birthday, Fransisna! 🎂",
                            wish = "Wishing you all the happiness and success!",
                            from = "💌 From Wildan"
                        )
                    }
                }
            }
        }
    }
}

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

@Composable
fun GreetingText(message: String, wish: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            fontSize = 52.sp,
            lineHeight = 64.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color(0xFF1565C0),
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(2f, 2f),
                    blurRadius = 5f
                )
            )
        )
        Text(
            text = wish,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = TextStyle(
                fontFamily = Poppins,
                color = Color(0xFF1E88E5)
            )
        )
        Text(
            text = from,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = TextStyle(fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0D47A1)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    HappyBirthdayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFE3F2FD)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GreetingText(
                    message = "🎉 Happy Birthday, Fransisna! 🎂",
                    wish = "Wishing you all the happiness and success!",
                    from = "💌 From Wildan"
                )
            }
        }
    }
}

