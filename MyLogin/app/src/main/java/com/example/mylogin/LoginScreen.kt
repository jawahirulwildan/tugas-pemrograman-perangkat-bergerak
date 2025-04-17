package com.example.mylogin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(){
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxHeight(0.85f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ){
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(20.dp))

                Image(painter = painterResource(id = R.drawable.login), contentDescription = "Login Image",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Halo, Generasi Emas", fontSize = 28.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Raihlah Mimpimu, Bersama Kursus In", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(value = email, onValueChange = {
                    email = it
                }, label = {
                    Text(text = "Email address")
                })

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(value = password, onValueChange = {
                    password = it
                }, label = {
                    Text(text = "Password")
                }, visualTransformation = PasswordVisualTransformation())

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    Log.i("Credential", "Email: $email Password: $password")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Login",fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Forgot Password?", fontSize = 16.sp,  modifier = Modifier.clickable {

                })

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Or sign in with", fontSize = 16.sp)

                Row (
                    modifier = Modifier.fillMaxWidth().padding(40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Image(painter = painterResource(id = R.drawable.facebookicon),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(60.dp).clickable {
                            //Facebook clicked
                        }
                    )

                    Image(painter = painterResource(id = R.drawable.googleicon),
                        contentDescription = "Google",
                        modifier = Modifier.size(60.dp).clickable {
                            //Google clicked
                        }
                    )

                    Image(painter = painterResource(id = R.drawable.twittericon),
                        contentDescription = "Twitter",
                        modifier = Modifier.size(60.dp).clickable {
                            //Twitter clicked
                        }
                    )
                }

            }

        }



    }
}