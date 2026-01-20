package com.example.groovemusic.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.groovemusic.R
import com.example.groovemusic.naviagtion.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(100)
        navController.navigate(Routes.HomeScreen.route){
            popUpTo(Routes.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.speaker),
            contentDescription = "Speaker",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "WelCome to \nGroove Music",
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

