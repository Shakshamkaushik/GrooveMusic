package com.example.groovemusic.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.groovemusic.R
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.screenitems.SongItem
import com.example.groovemusic.sealedclass.SearchScreenUISealed
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.SongViewModel

@Composable
fun SearchScreen(
    navController: NavHostController, musicPlayViewModel: MusicPlayViewModel,songViewModel: SongViewModel
) {
    val context = LocalContext.current
    val searchList = listOf("  All  ", "Songs", "Album", "Artist", "Playlist")
    var selectedIndex by remember { mutableIntStateOf(0) }


    val _globalSongsState = songViewModel.globalSongs.collectAsState().value
    val globalSongsState = _globalSongsState


    val keyboardController = LocalSoftwareKeyboardController.current

    var placeholderMessage by remember { mutableStateOf("Start typing to search") }
    val isSearchStarted = musicPlayViewModel.searchText.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.back_icon), null,
                modifier = Modifier.size(18.dp).clickable{
                    navController.popBackStack()
                }
            )
            BasicTextField(
                value = musicPlayViewModel.searchText,
                onValueChange = {
                    musicPlayViewModel.searchText = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(vertical = 10.dp)
                    .padding(start = 10.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {

                            songViewModel.getGlobalSearch(musicPlayViewModel.searchText)
                            keyboardController?.hide()
                            placeholderMessage = "Searching..."

                    }
                )
            )
            Icon(Icons.Default.Close, "Clear Text", modifier = Modifier.clickable{
                musicPlayViewModel.searchText= ""
            },
                tint = Color.Black)
        }

        LazyRow {
            itemsIndexed(searchList) { index, action ->
                val isSelected = index == selectedIndex
                Text(
                    action,
                    style = TextStyle(
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = if (isSelected) colorResource(R.color.light_blue) else colorResource(
                                R.color.light_white
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)
                        .clickable {
                            selectedIndex = index
                        }
                )
            }
        }

        when {
            !isSearchStarted -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Start typing to search", fontSize = 20.sp, color = Color.Black)
                }
            }

            globalSongsState is Results.Loading -> {


            }

            globalSongsState is Results.Success -> {
                val data =
                    globalSongsState.data?.globalSearchResponse


                if (data == null || (
                            data.songs?.results.isNullOrEmpty() &&
                                    data.albums?.results.isNullOrEmpty() &&
                                    data.artists?.results.isNullOrEmpty() &&
                                    data.playlists?.results.isNullOrEmpty()
                            )
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No Record Found", fontSize = 20.sp, color = Color.Black)
                    }
                } else {

                    LazyColumn {
                        when (searchList[selectedIndex]) {
                            "  All  " -> {

                                data.songs?.results?.let {
                                    items(it) { song ->
                                        SongItem(
                                            SearchScreenUISealed.SearchScreenSong(song),
                                            onItemClick = { songId ,_->
                                                musicPlayViewModel.saveSongId(context,songId)
                                                musicPlayViewModel.setCurrentPLayingSong(songId)
                                                navController.navigate(
                                                    Routes.PlayingScreen.createRoute(
                                                        songId,
                                                        "Search_Screen"
                                                    )
                                                )
                                            })
                                    }
                                }
                                data.albums?.results?.let {
                                    items(it) { album ->
                                        SongItem(
                                            SearchScreenUISealed.SearchScreenAlbum(album),
                                            onItemClick = { albumId  ,_->
                                                navController.navigate(
                                                    Routes.AlbumScreen.createRoute(
                                                        albumId
                                                    )
                                                )
                                            })
                                    }
                                }
                                data.artists?.results?.let {
                                    items(it) { artist ->
                                        SongItem(
                                            SearchScreenUISealed.SearchScreenArtist(artist),
                                            onItemClick = { artistId ,_->
                                                navController.navigate(
                                                    Routes.ArtistScreen.createRoute(
                                                        artistId
                                                    )
                                                )
                                            })
                                    }
                                }
                                data.playlists?.results?.let {
                                    items(it) { playlist ->
                                        SongItem(
                                            SearchScreenUISealed.SearchScreenPlaylist(
                                                playlist
                                            ), onItemClick = { playListId  ,_->
                                                navController.navigate(
                                                    Routes.PlayListDetails.createRoute(
                                                        playListId
                                                    )
                                                )
                                            })
                                    }
                                }
                            }

                            "Songs" -> data.songs?.results?.let {
                                items(it) { song ->
                                    SongItem(
                                        SearchScreenUISealed.SearchScreenSong(song),
                                        onItemClick = { songId ,_->
                                            musicPlayViewModel.saveSongId(context,songId)
                                            musicPlayViewModel.setCurrentPLayingSong(songId)
                                            navController.navigate(
                                                Routes.PlayingScreen.createRoute(
                                                    songId,
                                                    "Search_Screen"
                                                )
                                            )
                                        })
                                }
                            }

                            "Album" -> data.albums?.results?.let {
                                items(it) { album ->
                                    SongItem(
                                        SearchScreenUISealed.SearchScreenAlbum(album),
                                        onItemClick = { albumId ,_->
                                            navController.navigate(
                                                Routes.AlbumScreen.createRoute(
                                                    albumId
                                                )
                                            )
                                        })
                                }
                            }

                            "Artist" -> data.artists?.results?.let {
                                items(it) { artist ->
                                    SongItem(
                                        SearchScreenUISealed.SearchScreenArtist(artist),
                                        onItemClick = { artisId  ,_->
                                            navController.navigate(
                                                Routes.ArtistScreen.createRoute(
                                                    artisId
                                                )
                                            )
                                        })
                                }
                            }

                            "Playlist" -> data.playlists?.results?.let {
                                items(it) { playlist ->
                                    SongItem(
                                        SearchScreenUISealed.SearchScreenPlaylist(playlist),
                                        onItemClick = { playListId  ,_->
                                            navController.navigate(
                                                Routes.PlayListDetails.createRoute(
                                                    playListId
                                                )
                                            )
                                        })
                                }
                            }
                        }
                    }
                }
            }

            globalSongsState is Results.Failure -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Something went wrong", fontSize = 20.sp)
                }
            }

            else -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(placeholderMessage, fontSize = 20.sp)
                }
            }
        }

    }

}


