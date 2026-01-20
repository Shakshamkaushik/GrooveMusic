package com.example.groovemusic.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.sealedclass.ArtistItemUISealed
import com.example.groovemusic.screenitems.ArtistDetailItem
import com.example.groovemusic.viewmodel.ArtistViewModel
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import org.koin.compose.koinInject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumScreen(navController: NavHostController, albumId: String,musicPlayViewModel: MusicPlayViewModel) {
    val artistViewModel: ArtistViewModel = koinInject()
    val _artistAlbumDetail by artistViewModel.artistAlbumDetails.collectAsStateWithLifecycle()
    val artistAlbumDetail = _artistAlbumDetail

    LaunchedEffect(Unit) {
        artistViewModel.getArtistAlbumDetails(albumId)
    }
    var albumName by remember { mutableStateOf("") }
    var albumDesc by remember { mutableStateOf("") }
    var albumImage by remember { mutableStateOf("") }

    Scaffold(topBar = {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.top_bar_color))
                .padding(vertical = 15.dp),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft, null,
                tint = Color.White
            )
            Text(
                "Album Songs",
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )
        }

    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
                .padding(top = 10.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(albumImage), null,
                    modifier = Modifier
                        .size(width = 160.dp, height = 140.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(start = 5.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        albumName, style = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        albumDesc,
                        style = TextStyle(color = Color.Black, fontSize = 14.sp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    ElevatedButton(

                        onClick = {
                            musicPlayViewModel.previousPlayedSongId.clear()
                            musicPlayViewModel.lastRouteFromWhichScreen = "Album_Screen"
                            val albumSongId = musicPlayViewModel.albumSongList.random()
                            musicPlayViewModel.setCurrentPLayingSong(albumSongId)
                            navController.navigate(Routes.PlayingScreen.createRoute(albumSongId,"Album_Screen"))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.button_color),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Play", style = TextStyle(color = Color.White, fontSize = 20.sp))
                    }
                }
            }

            Text(
                "All Album Songs", style = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 10.dp)) {
                when (artistAlbumDetail) {

                    is Results.Loading -> {}
                    is Results.Success -> {
                        val albumSongList = artistAlbumDetail.data.data?.songs
                        musicPlayViewModel.albumSongList.clear()
                        albumSongList?.forEachIndexed { index, song ->
                            musicPlayViewModel.albumSongList.add(song.id.toString())
                        }
                        albumName = artistAlbumDetail.data.data?.name!!
                        albumDesc = artistAlbumDetail.data.data.description.toString()
                        albumImage = artistAlbumDetail.data.data.image?.last()?.url!!
                        artistAlbumDetail.data.data.songs.let { artistAlbumDetail ->

                            artistAlbumDetail?.let { detail ->
                                itemsIndexed(detail) { index, albumSongDetails ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.AlbumSongDetails(albumSongDetails),
                                        onItemCLick = { selectedSongId ->
                                            musicPlayViewModel.previousPlayedSongId.clear()
                                            musicPlayViewModel.lastRouteFromWhichScreen = "Album_Screen_Single_Track"
                                            musicPlayViewModel.setCurrentPLayingSong(selectedSongId)
                                            navController.navigate(Routes.PlayingScreen.createRoute(selectedSongId,"Album_Screen_Single_Track"))
                                        })
                                }
                            }
                        }
                    }

                    is Results.Failure -> {

                    }
                }
            }

        }
    }

}
