package com.example.groovemusic.screens


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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.ArtistDetailItem
import com.example.groovemusic.sealedclass.ArtistItemUISealed
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.PlaylistViewModel
import org.koin.compose.koinInject

@Composable
fun PlaylistScreen(
    navController: NavHostController,
    playListId: String,
    musicPlayViewModel: MusicPlayViewModel
) {

    val playlistViewModel: PlaylistViewModel = koinInject()
    val _playListDetails by playlistViewModel.playListDetails.collectAsState()
    val playListDetails = _playListDetails

    var playListName by remember { mutableStateOf("") }
    var playListDesc by remember { mutableStateOf("") }
    var playListImage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        playlistViewModel.getPlayListDetails(playListId)
    }



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
                    painter = rememberAsyncImagePainter(playListImage), null,
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
                        playListName, style = TextStyle(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        playListDesc,
                        style = TextStyle(color = Color.Black, fontSize = 14.sp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    ElevatedButton(
                        onClick = {
                            musicPlayViewModel.previousPlayedSongId.clear()
                            musicPlayViewModel.lastRouteFromWhichScreen = "Playlist_Screen"
                            val playlistFirstSongId = musicPlayViewModel.playListSongList.get(0)
                            musicPlayViewModel.setCurrentPLayingSong(playlistFirstSongId)
                            navController.navigate(
                                Routes.PlayingScreen.createRoute(
                                    playlistFirstSongId,
                                    "Playlist_Screen"
                                )
                            )

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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                when (playListDetails) {

                    is Results.Loading -> {

                    }

                    is Results.Success -> {
                        val playListSongs = playListDetails.data.playlistDetailResult?.songs
                        musicPlayViewModel.playListSongList.clear()
                        playListSongs?.forEachIndexed { index, song ->
                            musicPlayViewModel.playListSongList.add(song.id.toString())
                        }
                        playListName = playListDetails.data.playlistDetailResult?.name!!
                        playListDesc =
                            playListDetails.data.playlistDetailResult.description.toString()
                        playListImage =
                            playListDetails.data.playlistDetailResult.image?.last()?.url!!
                        playListDetails.data.playlistDetailResult.songs.let { playListDetails ->

                            playListDetails?.let { detail ->
                                itemsIndexed(detail) { index, playlistDetails ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.PlaylistDetails(playlistDetails),
                                        onItemCLick = { selectedSongId ->
                                            musicPlayViewModel.lastRouteFromWhichScreen =
                                                "Playlist_Screen_Single_Track"
                                            musicPlayViewModel.setCurrentPLayingSong(selectedSongId)
                                            navController.navigate(
                                                Routes.PlayingScreen.createRoute(
                                                    selectedSongId,
                                                    "Playlist_Screen_Single_Track"
                                                )
                                            )
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
