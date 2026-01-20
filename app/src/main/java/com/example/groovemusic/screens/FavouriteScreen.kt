package com.example.groovemusic.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.viewmodel.MusicPlayViewModel


@Composable
fun FavouriteScreen(navController: NavHostController, musicPlayViewModel: MusicPlayViewModel) {
    val context = LocalContext.current
    val favourites by musicPlayViewModel.favouriteSongsList.collectAsState()

    LaunchedEffect(Unit) {
        musicPlayViewModel.loadFavouriteSongs(context)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.top_bar_color))
                    .padding(vertical = 15.dp),
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft, null,
                    tint = Color.White,
                    modifier = Modifier.clickable{
                        navController.popBackStack()
                    }
                )
                Text(
                    "Favourite Songs",
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                )
            }
        },
        modifier = Modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {

                        if (musicPlayViewModel.favouriteSongIds.isNotEmpty()) {
                            musicPlayViewModel.previousPlayedSongId.clear()
                            val firstSongId = musicPlayViewModel.favouriteSongIds.get(0)
                            musicPlayViewModel.setCurrentPLayingSong(firstSongId)
                            navController.navigate(
                                Routes.PlayingScreen.createRoute(
                                    firstSongId,
                                    "Favourite_Screen"
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CD3D9))
                ) {
                    Text(
                        " Play  ",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
                Button(
                    onClick = {
                        if (musicPlayViewModel.favouriteSongIds.isNotEmpty()) {
                            val randomFavouriteId = musicPlayViewModel.favouriteSongIds.random()
                            musicPlayViewModel.setCurrentPLayingSong(randomFavouriteId)
                            navController.navigate(
                                Routes.PlayingScreen.createRoute(
                                    randomFavouriteId,
                                    "Favourite_Screen"
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CD3D9))
                ) {
                    Text(
                        "Shuffle", style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
            }

            LazyColumn {

                items(favourites) { favourite ->
                    FavouriteItem(
                        favourite, onItemClick = { songId ->
                            musicPlayViewModel.setCurrentPLayingSong(songId)
                            musicPlayViewModel.lastRouteFromWhichScreen = "Favourite_Screen"
                            navController.navigate(
                                Routes.PlayingScreen.createRoute(
                                    songId,
                                    "Favourite_Screen"
                                )
                            )
                        },
                        removeFavourite = { songId ->
                            musicPlayViewModel.removeFavouriteSongs(context, songId)
                        })
                }
            }
        }
    }

}

@Composable
fun FavouriteItem(
    favouriteSongs: Favourite,
    onItemClick: ((String) -> Unit),
    removeFavourite: ((String) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 6.dp)
            .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(8.dp))
            .clickable {
                onItemClick.invoke(favouriteSongs.favouriteSongID.toString())
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            rememberAsyncImagePainter(favouriteSongs.favouriteSongImage),
            null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                favouriteSongs.favouriteSongName.toString(),
                modifier = Modifier.padding(bottom = 3.dp),
                style = TextStyle(
                    fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold
                )
            )
            Text(
                favouriteSongs.favouriteSongDesc.toString(),
                modifier = Modifier
                    .padding(top = 3.dp)
                    .basicMarquee(),
                style = TextStyle(
                    fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1
            )
        }
        Icon(
            Icons.Default.Favorite, null,
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    removeFavourite.invoke(favouriteSongs.favouriteSongID.toString())
                },
            tint = Color.Red
        )
    }
}

