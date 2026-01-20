package com.example.groovemusic.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.ArtistDetailItem
import com.example.groovemusic.sealedclass.ArtistItemUISealed
import com.example.groovemusic.viewmodel.ArtistViewModel
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import org.koin.compose.koinInject

@Composable
fun ArtistDetailsScreen(
    navController: NavHostController,
    artistId: String,
    fetchSong: Boolean,
    artistName: String,
    musicPlayViewModel: MusicPlayViewModel
) {

    val listState = rememberLazyListState()
    val artistViewModel: ArtistViewModel = koinInject()
    val _artistSongsList by artistViewModel.artistSongsList.collectAsStateWithLifecycle()
    val artistSongsList = _artistSongsList

    val _artistAlbumList by artistViewModel.artistAlbumList.collectAsStateWithLifecycle()
    val artistAlbumList = _artistAlbumList
    LaunchedEffect(Unit) {
        if (fetchSong) {
            artistViewModel.getArtistSongList(artistId)
        } else {
            artistViewModel.getArtistAlbumList(artistId)
        }
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(com.example.groovemusic.R.color.top_bar_color))
                .padding(vertical = 15.dp),
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, null,
                modifier = Modifier.clickable{
                    navController.popBackStack()
                })
            Text(
                artistName, modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(color = Color.White, fontSize = 20.sp)
            )
        }
    }) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(state = listState) {
                if (fetchSong) {
                    when (artistSongsList) {

                        is Results.Loading -> {
                        }

                        is Results.Success -> {
                            artistSongsList.data.data.let { songsListData ->
                                itemsIndexed(songsListData.songs) { index, songDetail ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.ArtistSongsList(
                                            songDetail,
                                            index + 1
                                        )
                                    ) { songId ->
                                        musicPlayViewModel.lastRouteFromWhichScreen =
                                            "Artist_Album_Detail_Screen"
                                        musicPlayViewModel.setCurrentPLayingSong(songId)
                                        navController.navigate(
                                            Routes.PlayingScreen.createRoute(
                                                songId,
                                                "Artist_Album_Detail_Screen"
                                            )
                                        )

                                    }
                                }
                            }
                        }

                        is Results.Failure -> {
                        }
                    }
                } else {
                    when (artistAlbumList) {
                        is Results.Loading -> {

                        }

                        is Results.Success -> {
                            artistAlbumList.data.data.let { artistAlbumList ->
                                itemsIndexed(artistAlbumList.albums) { index, albumDetail ->
                                    ArtistDetailItem(
                                        ArtistItemUISealed.ArtistDetailAlbum(
                                            albumDetail,
                                            index + 1
                                        )
                                    ) { albumId ->
                                        navController.navigate(
                                            Routes.AlbumScreen.createRoute(
                                                albumId
                                            )
                                        )
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
    LaunchedEffect(listState) {
        snapshotFlow {
            val layout = listState.layoutInfo
            val lastVisibleIndex = layout.visibleItemsInfo.lastOrNull()?.index
            lastVisibleIndex == layout.totalItemsCount - 1


        }.collect { isAtEnd ->
            if (isAtEnd) {

                if (fetchSong) {
                    artistViewModel.getArtistSongList(artistId)
                } else {
                    artistViewModel.getArtistAlbumList(artistId)
                }
            }
        }
    }
}

