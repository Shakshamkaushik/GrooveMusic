package com.example.groovemusic.screens

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.HomeScreenItem
import com.example.groovemusic.sealedclass.HomeScreenUISealed
import com.example.groovemusic.viewmodel.AlbumViewModel
import com.example.groovemusic.viewmodel.ArtistViewModel
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.PlaylistViewModel
import com.example.groovemusic.viewmodel.SharedViewModel
import com.example.groovemusic.viewmodel.SongViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    musicPlayViewModel: MusicPlayViewModel
) {
    val cardGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFC9F2EC),
            Color(0xFFDDEBFA),
            Color(0xFFF2D6E8),
        ),
        start = Offset(0f, 0f),
        end = Offset.Infinite,
    )
val currentPlayingSongId by musicPlayViewModel.currentPlayingSongId.collectAsState()

    val verticalScroll = rememberScrollState()


    val songVIewModel: SongViewModel = koinViewModel()
    val artistViewModel: ArtistViewModel = koinViewModel()
    val playlistViewModel: PlaylistViewModel = koinViewModel()
    val albumViewModel: AlbumViewModel = koinViewModel()


    val _songsListState by songVIewModel.songsList.collectAsState()
    val _artistListState by artistViewModel.artistList.collectAsState()
    val _playListState by playlistViewModel.playList.collectAsState()
    val _albumListState by albumViewModel.albumList.collectAsState()
    val songsListState = _songsListState
    val artistListState = _artistListState
    val playListState = _playListState
    val albumListState = _albumListState

    val context = LocalContext.current
musicPlayViewModel.loadFavouriteSongs(context)

    val song by sharedViewModel.selectedSong.collectAsState()





    LaunchedEffect(Unit) {
        songVIewModel.getSongsList("")
        artistViewModel.getArtist("")
        playlistViewModel.getPlayList("")
        albumViewModel.getAlbum("")

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        Column(modifier = Modifier.verticalScroll(verticalScroll)) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Find the best \nMusic for you", style = TextStyle(
                        color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    ), modifier = Modifier.padding(start = 10.dp)
                )

                Image(
                    painter = painterResource(R.drawable.point_right),
                    contentDescription = null,
                    modifier = Modifier.size(height = 100.dp, width = 80.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .background(color = Color(0xFF5F9598), RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp)
                        .clickable { navController.navigate(Routes.FavouriteScreen.route) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Star, null, tint = Color.White)
                    Text("Favourites", color = Color.White)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .background(color = Color(0xFF5F9598), RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp)
                        .clickable { navController.navigate(Routes.AddPlaylistScreen.createEmptyRoute()) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.playlist), null,
                        modifier = Modifier.size(25.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Text("Playlist", color = Color.White)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .background(color = Color(0xFF5F9598), RoundedCornerShape(10.dp))
                        .padding(vertical = 5.dp)
                        .clickable {
                            Toast.makeText(
                                context, "Currently it is in Under Work.", Toast.LENGTH_SHORT
                            ).show()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.recent), null,
                        modifier = Modifier.size(25.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Text("Recent", color = Color.White)
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, shape = RoundedCornerShape(29.dp), color = Color.Black
                    )
                    .padding(vertical = 5.dp)
                    .clickable {
                        navController.navigate(Routes.SearchScreen.route)

                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Search Song Album Artist",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    style = TextStyle(color = Color.Black)
                )
                Icon(
                    Icons.Default.Search, null,
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Popular Songs", style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                ), modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            when (songsListState) {

                is Results.Loading -> {

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(15) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(width = 140.dp, height = 170.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerLoading()
                            )
                        }
                    }

                }


                is Results.Success -> {
                    val sog = songsListState.data.data.results
                    musicPlayViewModel.popularSongsIds.clear()
                    sog.forEachIndexed { index, result ->
                        musicPlayViewModel.saveSongId(context, result.id)
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {

                        val songResult = songsListState.data.data.results.take(10)
                        items(songResult) { item ->
                            HomeScreenItem(
                                HomeScreenUISealed.HomeScreenSong(item),
                                onItemCLick = { songId ->
                                    musicPlayViewModel.previousPlayedSongId.clear()
                                    musicPlayViewModel.lastRouteFromWhichScreen = "Home_Screen"
                                    musicPlayViewModel.setCurrentPLayingSong(songId)
                                    navController.navigate(
                                        Routes.PlayingScreen.createRoute(
                                            songId,
                                            "Home_Screen"
                                        )
                                    )

                                })
                        }
                    }
                }


                is Results.Failure -> {
                    Text(
                        text = songsListState.message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Popular Artists", style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                ), modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            when (artistListState) {

                is Results.Loading -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(15) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(width = 140.dp, height = 170.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerLoading()
                            )
                        }
                    }
                }

                is Results.Success -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(artistListState.data.data.artistResult) { item ->

                            HomeScreenItem(
                                item = HomeScreenUISealed.HomeScreenArtist(item),
                                onItemCLick = { artistId ->
                                    navController.navigate(Routes.ArtistScreen.createRoute(artistId))

                                }
                            )
                        }
                    }
                }

                is Results.Failure -> {


                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Popular Album", style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                ), modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            when (albumListState) {
                Results.Loading -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(15) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(width = 140.dp, height = 170.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerLoading()
                            )
                        }
                    }
                }

                is Results.Success -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(albumListState.data.data.albumResult) { item ->
                            HomeScreenItem(
                                item = HomeScreenUISealed.HomeScreenAlbum(item),
                                onItemCLick = { albumId ->
                                    navController.navigate(Routes.AlbumScreen.createRoute(albumId))
                                }
                            )
                        }
                    }

                }

                is Results.Failure -> {

                }
            }


            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Popular Playlist", style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                ), modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            when (playListState) {

                is Results.Loading -> {

                }

                is Results.Success -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        items(playListState.data.data.playlistResult) { item ->
                            HomeScreenItem(
                                item = HomeScreenUISealed.HomeScreenSongPlaylist(item),
                                onItemCLick = { playListId ->
                                    navController.navigate(
                                        Routes
                                            .PlayListDetails.createRoute(playListId)
                                    )
                                }
                            )
                        }
                    }

                }

                is Results.Failure -> {

                }
            }
            Spacer(modifier = Modifier.height(90.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    brush = cardGradient,
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                )
                .align(Alignment.BottomCenter)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    rememberAsyncImagePainter(song?.image),
                    null,
                    modifier = Modifier
                        .size(65.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate(
                                Routes.PlayingScreen.createRoute(
                                    "",
                                    musicPlayViewModel.lastRouteFromWhichScreen
                                )
                            )
                        },
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        song?.name.toString(), modifier = Modifier.padding(bottom = 3.dp),
                        style = TextStyle(
                            fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                    Text(
                        song?.desc.toString(), modifier = Modifier.padding(top = 3.dp),
                        style = TextStyle(
                            fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1
                    )
                }
                Icon(
                    if (musicPlayViewModel.isFavourite(currentPlayingSongId))
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder, null,
                    modifier = Modifier.clickable{

                    },
                    tint = if(musicPlayViewModel.isFavourite(currentPlayingSongId)){
                        Color.Red
                    }else{
                        Color.DarkGray
                    }
                )
            }
        }
    }

}



fun Modifier.shimmerLoading(
    durationMillis: Int = 1000
): Modifier = composed {

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translate by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )

    drawBehind {
        val width = size.width
        val height = size.height

        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.3f),
                    Color.LightGray.copy(alpha = 0.9f),
                    Color.LightGray.copy(alpha = 0.3f)
                ),
                start = Offset(width * (translate - 1), 0f),
                end = Offset(width * translate, height)
            )
        )
    }
}



