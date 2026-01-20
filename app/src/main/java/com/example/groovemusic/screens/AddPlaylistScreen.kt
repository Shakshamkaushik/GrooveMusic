package com.example.groovemusic.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Iterations
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.model.storage.PlayLists
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import java.util.UUID
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylistScreen(
    navController: NavHostController,
    songDetails: Favourite?,
    musicPlayViewModel: MusicPlayViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var playlistName by remember { mutableStateOf("") }
    val playLists by musicPlayViewModel.playLists.collectAsState()
    LaunchedEffect(Unit) {

        musicPlayViewModel.getAllPlayList(context)
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
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft, null, tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
                Text(
                    "Playlists",
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                )
            }
        }, modifier = Modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CD3D9))
            ) {
                Text(
                    "Create New Playlist", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontSynthesis = FontSynthesis.Weight,
                        shadow = Shadow(color = Color.Black, offset = Offset(10))
                    )
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(playLists) { playlistItem ->
                    PLayListItem(playlistItem, onDelete = {
                        musicPlayViewModel.deletePlayList(
                            context,
                            playlistItem.playlistId.toString()
                        )
                    }, onAdd = { playlistItem ->
                        val updatedSongDetails = songDetails?.let {
                            Favourite(
                                favouriteSongName = songDetails.favouriteSongName,
                                favouriteSongDesc = songDetails.favouriteSongDesc,
                                favouriteSongImage = songDetails.favouriteSongImage,
                                favouriteSongID = songDetails.favouriteSongID,
                                playlistId = playlistItem.playlistId
                            )
                        }
                        if (updatedSongDetails != null) {
                            musicPlayViewModel.addSongToPlayList(
                                context,
                                playlistItem.playlistId.toString(),
                                updatedSongDetails,
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please Select the Song first!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }, onItemCLick = { playListItem ->

                        navController.navigate(
                            Routes.PlayListDetailScreen.createRouteToPlaylistDetailScreen(
                                playListItem.playlistId.toString(),
                                playListItem.playlistName.toString()
                            )
                        )
                    })
                }
            }

        }
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "Create New Playlist",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = playlistName, onValueChange = {
                                playlistName = it
                            }, modifier = Modifier.fillMaxWidth(), placeholder = {
                                Text("Enter Playlist Name")
                            }, colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { showDialog = false }) {
                                Text("Cancel")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {

                                    musicPlayViewModel.createPlayList(
                                        context,
                                        UUID.randomUUID().toString(),
                                        playlistName
                                    )
                                    playlistName = ""
                                    showDialog = false
                                }) {
                                Text("Add")
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PLayListItem(
    playlistItem: PlayLists,
    onDelete: ((PlayLists) -> Unit),
    onAdd: ((PlayLists) -> Unit),
    onItemCLick: ((PlayLists) -> Unit)
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onItemCLick.invoke(playlistItem)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imagePainter = if (playlistItem.playListSongs.isNotEmpty()) {
                rememberAsyncImagePainter(
                    playlistItem.playListSongs.first().favouriteSongImage
                )
            } else {
                painterResource(R.drawable.playlist_cover)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    imagePainter,
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "playlist_option",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            menuExpanded = true
                        }
                    )


                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                menuExpanded = false

                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                menuExpanded = false
                                onDelete.invoke(playlistItem)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add Song") },
                            onClick = {
                                menuExpanded = false
                                onAdd.invoke(playlistItem)
                            }
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            playlistItem.playlistName.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp)
                .basicMarquee(Iterations.minutes.toInt(DurationUnit.MINUTES)),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontSynthesis = FontSynthesis.Weight,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Total Songs - ${playlistItem.playListSongs.size}",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontSynthesis = FontSynthesis.Weight
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
    }


}

