package com.example.groovemusic.screenitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.sealedclass.HomeScreenUISealed

@Composable
fun HomeScreenItem(item: HomeScreenUISealed, onItemCLick: ((String) -> Unit)) {

    Box(
        modifier = Modifier
            .size(160.dp)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray)
            .clickable {
                when (item) {
                    is HomeScreenUISealed.HomeScreenAlbum -> onItemCLick.invoke(item.albumResult.id)
                    is HomeScreenUISealed.HomeScreenArtist -> {
                        onItemCLick.invoke(item.artistResult.id)
                    }

                    is HomeScreenUISealed.HomeScreenSong -> onItemCLick.invoke(item.songResult.id)
                    is HomeScreenUISealed.HomeScreenSongPlaylist -> {
                        onItemCLick.invoke(item.playlistResult.id)
                    }
                }
            }
    ) {


        Image(
            painter = when (item) {
                is HomeScreenUISealed.HomeScreenAlbum -> {
                    rememberAsyncImagePainter(item.albumResult.image.last().url)
                }

                is HomeScreenUISealed.HomeScreenArtist -> {
                    rememberAsyncImagePainter(item.artistResult.image.last().url)
                }

                is HomeScreenUISealed.HomeScreenSong -> {
                    rememberAsyncImagePainter(item.songResult.image.last().url)
                }

                is HomeScreenUISealed.HomeScreenSongPlaylist -> {
                    rememberAsyncImagePainter(item.playlistResult.image.last().url)
                }
            },
            contentDescription = "Music Album",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = when (item) {
                        is HomeScreenUISealed.HomeScreenAlbum -> {
                            item.albumResult.name
                        }

                        is HomeScreenUISealed.HomeScreenArtist -> {
                            item.artistResult.name
                        }

                        is HomeScreenUISealed.HomeScreenSong -> {
                            item.songResult.name.substringBefore("(").trim()
                        }

                        is HomeScreenUISealed.HomeScreenSongPlaylist -> {
                            item.playlistResult.name
                        }
                    },
                    color = Color.White,
                    style = TextStyle(fontSize = 12.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    when (item) {
                        is HomeScreenUISealed.HomeScreenAlbum -> {
                            ""

                        }

                        is HomeScreenUISealed.HomeScreenArtist -> {
                            ""
                        }

                        is HomeScreenUISealed.HomeScreenSong -> {
                            item.songResult.label.substringBefore("(").trim()
                        }

                        is HomeScreenUISealed.HomeScreenSongPlaylist -> {
                            "Total Songs - ${item.playlistResult.songCount}"
                        }
                    },
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}