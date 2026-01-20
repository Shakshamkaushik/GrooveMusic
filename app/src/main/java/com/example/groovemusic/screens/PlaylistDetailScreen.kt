package com.example.groovemusic.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.SongItem
import com.example.groovemusic.sealedclass.SearchScreenUISealed
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun PlaylistDetailScreen(
    navController: NavHostController,
    playlistId: String,
    playlistName: String,
    musicPlayViewModel: MusicPlayViewModel
) {
    val context = LocalContext.current
    val playlistSongs by musicPlayViewModel.currentPlaylistSongs.collectAsState()
    LaunchedEffect(Unit) {
        musicPlayViewModel.getSongsFromPlayList(playlistId)
    }
    val scrollBehavior = rememberCollapsingToolbarScaffoldState()
    val progress = scrollBehavior.toolbarState.progress

    CollapsingToolbarScaffold(
        state = scrollBehavior,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val collapsedColor = MaterialTheme.colorScheme.primary
            val backgroundColor = collapsedColor.copy(alpha = 1f - progress)
            val cardGradient = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFC9F2EC),
                    Color(0xFFDDEBFA),
                    Color(0xFFF2D6E8),
                ),
                start = Offset(0f, 0f),
                end = Offset.Infinite,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = cardGradient,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    )
            ) {
                Image(
                    painter = if (playlistSongs.isNotEmpty()) {
                        rememberAsyncImagePainter(playlistSongs.first().favouriteSongImage
                        )
                    } else {
                        painterResource(R.drawable.playlist_cover)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(310.dp)
                        .padding(80.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .align(Alignment.Center)
                        .pin(),
                    contentScale = ContentScale.Crop,
                    alpha = progress
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(progress)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp) // collapsed height
                    .pin()
                    .background(backgroundColor)
            )


            Column(
                modifier = Modifier
                    .padding(top = if (progress == 1f) 0.dp else 42.dp)
                    .road(
                        whenCollapsed = Alignment.CenterStart,
                        whenExpanded = Alignment.BottomCenter
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = if (progress == 1f)
                    Alignment.CenterHorizontally
                else
                    Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .road(
                            whenCollapsed = Alignment.CenterStart,
                            whenExpanded = Alignment.BottomCenter
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (progress == 1f)
                        Arrangement.Center
                    else
                        Arrangement.Start
                ) {
                    if (progress == 0f) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }


                    Text(
                        text = playlistName,
                        fontSize = (18f + (30f - 18f) * progress).sp,
                        color = Color.White,
                        modifier = Modifier
                    )
                }


                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .alpha(progress),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painterResource(R.drawable.music_quality), null,
                        modifier = Modifier.size(28.dp)
                    )
                    Image(
                        painterResource(R.drawable.play_icon), null,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                musicPlayViewModel.previousPlayedSongId.clear()
                                val songId = musicPlayViewModel.playListSongIds.first()
                                musicPlayViewModel.setCurrentPLayingSong(songId)
                                navController.navigate(
                                    Routes.PlayingScreen.createRoute(
                                        songId,
                                        "PlayList_Detail_Screen"
                                    )
                                )
                            }
                    )
                    Image(
                        painterResource(R.drawable.download_icon), null,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (progress == 0f) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painterResource(R.drawable.play_icon),
                        null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                musicPlayViewModel.previousPlayedSongId.clear()
                                val songId = musicPlayViewModel.playListSongIds.first()
                                musicPlayViewModel.setCurrentPLayingSong(songId)
                                navController.navigate(
                                    Routes.PlayingScreen.createRoute(
                                        songId,
                                        "PlayList_Detail_Screen"
                                    )
                                )
                            },
                    )
                }
            }
           if (playlistSongs.size > 0){
               LazyColumn(
                   modifier = Modifier
                       .padding(top = 10.dp)
                       .fillMaxWidth()
                       .padding(10.dp)
               ) {
                   items(playlistSongs) {
                       SongItem(
                           SearchScreenUISealed.PlayListSongs(it),
                           onItemClick = { playlistId, songId ->
                               if (playlistId.isEmpty()) {
                                   musicPlayViewModel.setCurrentPLayingSong(songId)
                                   musicPlayViewModel.lastRouteFromWhichScreen =
                                       "PlayList_Detail_Screen"
                                   navController.navigate(
                                       Routes.PlayingScreen.createRoute(
                                           songId,
                                           "PlayList_Detail_Screen"
                                       )
                                   )
                               } else {
                                   musicPlayViewModel.removeSongFromPLayList(
                                       context,
                                       playlistId,
                                       songId
                                   )
                               }

                           })
                   }
               }
           }else{
               Box(modifier = Modifier.fillMaxSize(),
                   contentAlignment = Alignment.Center){
                   Text("No Record Found", style= TextStyle( color = Color.Black, fontSize = 18.sp))
               }
           }
        }
    }
}
