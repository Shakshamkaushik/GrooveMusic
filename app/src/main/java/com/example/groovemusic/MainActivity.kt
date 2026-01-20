package com.example.groovemusic

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.groovemusic.di.repoModules
import com.example.groovemusic.di.singleModules
import com.example.groovemusic.di.viewmodelModules
import com.example.groovemusic.naviagtion.NavigationGraph
import com.example.groovemusic.service.MusicService
import com.example.groovemusic.ui.theme.GrooveMusicTheme
import com.example.groovemusic.utils.NumberUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    private val postNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->

            when (permission) {
                true -> {
                    android.Manifest.permission.POST_NOTIFICATIONS
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPostNotificationPerission()
                    } else {
                        Toast.makeText(this, "Permisison Granted", Toast.LENGTH_SHORT).show()
                    }
                }

                false -> {
                    Toast.makeText(this, "Permisison Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun requestPermissions() {

        val permissions =
            android.Manifest.permission.POST_NOTIFICATIONS



        if (ContextCompat.checkSelfPermission(
                this,
                permissions
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            postNotificationPermission.launch(permissions)
        } else {

            // startLocationUpdates()
        }
    }

    private fun requestPostNotificationPerission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            postPermiision.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val postPermiision = registerForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {

            Toast.makeText(this, "Background location permission Granted", Toast.LENGTH_SHORT)
                .show()
        } else {

            Toast.makeText(this, "Background location permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissions()
        val intent = Intent(this@MainActivity, MusicService::class.java)
        ContextCompat.startForegroundService(this@MainActivity, intent)
        setContent {
            GrooveMusicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                 if (NumberUtils.isInternetConnected(this)){

                     val navController = rememberNavController()
                     NavigationGraph(navController, innerPadding)
                 }else{
                     InterNetworkIssue()
                 }
                }
            }
        }
    }


    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@MainActivity, MusicService::class.java))
    }
}

@Composable
fun InterNetworkIssue() {
    Column(
        modifier = Modifier.background(Color.White)
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_blue))) {
            Text("Hey buddy! It looks like you're not connected to the internet. Please check your network connection and try again",
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                style = TextStyle(fontSize = 20.sp, color = Color.Black))
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    GrooveMusicTheme {
        InterNetworkIssue()
    }
}

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(singleModules, repoModules, viewmodelModules)
        }
    }
}
