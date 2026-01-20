package com.example.groovemusic.screenitems

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Iterations
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.sealedclass.SearchScreenUISealed
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Composable
fun SongItem(item: SearchScreenUISealed, onItemClick: ((String, String) -> Unit)) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    var isFromPlayList by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable {
                when (item) {
                    is SearchScreenUISealed.SearchScreenAlbum -> {
                        onItemClick.invoke(item.globalArtist.id.toString(), "")
                    }

                    is SearchScreenUISealed.SearchScreenArtist -> {
                        onItemClick.invoke(item.globalAlbum.id.toString(), "")
                    }

                    is SearchScreenUISealed.SearchScreenPlaylist -> {
                        onItemClick.invoke(item.globalPlaylist.id.toString(), "")
                    }

                    is SearchScreenUISealed.SearchScreenSong -> {
                        onItemClick.invoke(item.globalSongs.id.toString(), "")
                    }

                    is SearchScreenUISealed.PlayListSongs -> {
                        onItemClick.invoke( "",item.playlist.favouriteSongID.toString())
                    }
                }
            },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    when (item) {
                        is SearchScreenUISealed.SearchScreenAlbum -> item.globalArtist.image?.last()?.url
                        is SearchScreenUISealed.SearchScreenArtist -> item.globalAlbum.image?.last()?.url
                        is SearchScreenUISealed.SearchScreenPlaylist -> item.globalPlaylist.image?.last()?.url
                        is SearchScreenUISealed.SearchScreenSong -> item.globalSongs.image?.last()?.url
                        is SearchScreenUISealed.PlayListSongs -> {
                            isFromPlayList = true
                            item.playlist.favouriteSongImage.toString()
                        }
                    }
                ),
                null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 3.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    when (item) {
                        is SearchScreenUISealed.SearchScreenAlbum -> item.globalArtist.title.toString()
                        is SearchScreenUISealed.SearchScreenArtist -> item.globalAlbum.title.toString()
                        is SearchScreenUISealed.SearchScreenPlaylist -> item.globalPlaylist.title.toString()
                        is SearchScreenUISealed.SearchScreenSong -> item.globalSongs.title.toString()
                        is SearchScreenUISealed.PlayListSongs -> item.playlist.favouriteSongName.toString()

                    },
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(top = 5.dp),
                    maxLines = 1
                )
                Text(
                    when (item) {
                        is SearchScreenUISealed.SearchScreenAlbum -> item.globalArtist.type.toString()
                        is SearchScreenUISealed.SearchScreenArtist -> item.globalAlbum.type.toString()
                        is SearchScreenUISealed.SearchScreenPlaylist -> item.globalPlaylist.type.toString()
                        is SearchScreenUISealed.SearchScreenSong -> item.globalSongs.type.toString()
                        is SearchScreenUISealed.PlayListSongs -> item.playlist.favouriteSongDesc.toString()
                    },
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .basicMarquee(Iterations.minutes.toInt(DurationUnit.MINUTES)),
                    maxLines = 1
                )
            }
            Box {
                Icon(
                    Icons.Default.MoreVert, null,
                    tint = Color.Black,
                    modifier = Modifier.clickable {
                        showMenu = true
                    })
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Add to Favourite") },
                        onClick = {
                            showMenu = false
                            Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
                        }
                    )
                    if (isFromPlayList) {
                        DropdownMenuItem(
                            text = { Text("Remove from Playlist") },
                            onClick = {
                                showMenu = false
                                when (item) {
                                    is SearchScreenUISealed.SearchScreenAlbum -> {}
                                    is SearchScreenUISealed.SearchScreenArtist -> {}
                                    is SearchScreenUISealed.SearchScreenPlaylist -> {}
                                    is SearchScreenUISealed.SearchScreenSong -> {}
                                    is SearchScreenUISealed.PlayListSongs -> {
                                        onItemClick.invoke(
                                            item.playlist.playlistId.toString(),
                                            item.playlist.favouriteSongID.toString()
                                        )
                                    }
                                }
                            }
                        )
                    } else {
                        DropdownMenuItem(
                            text = { Text("Add to Playlist") },
                            onClick = {
                                showMenu = false
                                Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
                            }

                        )
                    }
                }
            }
        }
    }
}
