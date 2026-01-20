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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.ArtistDetailItem
import com.example.groovemusic.sealedclass.ArtistItemUISealed
import com.example.groovemusic.viewmodel.ArtistViewModel
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.compose.koinInject


@OptIn(ExperimentalToolbarApi::class)
@Composable
fun ArtistScreen(
    navHostController: NavHostController,
    artistId: String,
    musicPlayViewModel: MusicPlayViewModel
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val scope = rememberCoroutineScope()
    val progress = state.toolbarState.progress
    val artistViewModel: ArtistViewModel = koinInject()
    val _artistDetail by artistViewModel.artistDetails.collectAsStateWithLifecycle()
    val artistDetail = _artistDetail

    val _artistAlbumDetail by artistViewModel.artistAlbumDetails.collectAsStateWithLifecycle()
    val artistAlbumDetail = _artistAlbumDetail
    var artistImg by remember { mutableStateOf("") }
    var artistName by remember { mutableStateOf("") }
    var artistAlbumId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        artistViewModel.getArtistDetails(artistId)
    }

    LaunchedEffect(artistAlbumId, artistAlbumDetail) {
        when (artistAlbumDetail) {

            is Results.Failure -> {}
            Results.Loading -> {}
            is Results.Success -> {

                if (artistAlbumDetail != null) {
                    musicPlayViewModel.lastRouteFromWhichScreen = "Artist_Screen"
                    val songId = artistAlbumDetail.data.data?.songs?.first()?.id.toString()
                    musicPlayViewModel.setCurrentPLayingSong(songId)
                    navHostController.navigate(
                        Routes.PlayingScreen.createRoute(
                            songId, "Artist_Screen"
                        )
                    )
                }
            }
        }
    }




    LaunchedEffect(state.toolbarState.progress) {
        val progress = state.toolbarState.progress
        if (!state.toolbarState.isScrollInProgress && progress > 0f && progress < 1f) {
            if (progress < 0.5f) {
                scope.launch {
                    state.toolbarState.collapse()
                }
            } else {
                scope.launch {
                    state.toolbarState.expand()
                }
            }
        }
    }
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {

            val collapsedColor = MaterialTheme.colorScheme.primary
            val backgroundColor = collapsedColor.copy(alpha = 1f - progress)

            Image(
                painter = rememberAsyncImagePainter(artistImg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.White)
                    .pin(),
                contentScale = ContentScale.Crop,
                alpha = progress
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(progress)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .pin()
                    .background(backgroundColor)
            )


            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .road(
                        whenCollapsed = Alignment.CenterStart,
                        whenExpanded = Alignment.BottomStart
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (progress == 0f) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }


                Text(
                    text = artistName,
                    fontSize = (18f + (30f - 18f) * progress).sp,
                    color = Color.White,
                )
            }

        }
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {


                // Top Songs header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Top Songs", style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Text(
                            "See More", style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.clickable{
                                navHostController.navigate(Routes.ArtistDetailScreen.createRoute(artistId, true,artistName))
                            }
                        )
                    }
                }


                // Top Songs list
                when (artistDetail) {

                    is Results.Loading -> {
                        items(36) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 10.dp)
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerLoading()
                            )
                        }
                    }

                    is Results.Success -> {
                        musicPlayViewModel.artistSongList.clear()
                        artistDetail.data.data?.topSongs?.forEach { action ->
                            musicPlayViewModel.artistSongList.add(action.id!!)
                        }

                        artistImg = artistDetail.data.data?.image?.last()?.url.toString()
                        artistName = artistDetail.data.data?.name.toString()
                        artistDetail.data.data?.topSongs.let { artistDetailSongs ->
                            artistDetailSongs?.let {
                                itemsIndexed(artistDetailSongs) { index, songDetails ->

                                    ArtistDetailItem(
                                        ArtistItemUISealed.Song(
                                            songDetails,
                                            position = index + 1
                                        ), onItemCLick = { songId ->
                                            musicPlayViewModel.previousPlayedSongId.clear()
                                            musicPlayViewModel.lastRouteFromWhichScreen =
                                                "Artist_Screen"
                                            musicPlayViewModel.setCurrentPLayingSong(songId)
                                            navHostController.navigate(
                                                Routes.PlayingScreen.createRoute(
                                                    songId, "Artist_Screen"
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
                // Top Albums header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Top Albums", style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        Text(
                            "See More", style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),modifier = Modifier.clickable{
                                navHostController.navigate(Routes.ArtistDetailScreen.createRoute(artistId, false,artistName))
                            }
                        )
                    }
                }

                //Top Albums list
                when (artistDetail) {

                    is Results.Loading -> {

                        items(15) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerLoading()
                            )
                        }

                    }

                    is Results.Success -> {
                        artistDetail.data.data?.topAlbums.let { artistDetailAlbum ->
                            artistDetailAlbum?.let {
                                itemsIndexed(artistDetailAlbum) { index, albumDetails ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.Album(
                                            albumDetails,
                                            position = index + 1
                                        ), onItemCLick = { albumId ->
                                            navHostController.navigate(
                                                Routes.AlbumScreen.createRoute(
                                                    albumId
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

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Single Songs", style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                }
                when (artistDetail) {

                    is Results.Loading -> {

                    }

                    is Results.Success -> {

                        artistDetail.data.data?.singles.let { artistDetailSingles ->
                            artistDetailSingles?.let {
                                itemsIndexed(
                                    artistDetailSingles
                                ) { index, singleDetails ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.Singles(
                                            singleDetails,
                                            position = index + 1
                                        ), onItemCLick = { singleSongId ->
                                            navHostController.navigate(
                                                Routes.AlbumScreen.createRoute(
                                                    singleSongId
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



