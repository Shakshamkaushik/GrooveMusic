package com.example.groovemusic.screenitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Iterations
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.sealedclass.ArtistItemUISealed
import com.example.groovemusic.utils.NumberUtils.formatNumber
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Composable
fun ArtistDetailItem(item: ArtistItemUISealed, onItemCLick: ((String) -> Unit)) {

    var songDesc by remember { mutableStateOf("") }
    var albumDesc by remember { mutableStateOf("") }
    var singleSongDesc by remember { mutableStateOf("") }

    when (item) {
        is ArtistItemUISealed.Album -> {
            albumDesc = "${item.album.year} | Total Songs ${item.album.songCount}"

        }

        is ArtistItemUISealed.Song -> {
            songDesc = "${item.song.year} | ${item.song.label}"
        }

        is ArtistItemUISealed.Singles -> {
            singleSongDesc = "Year ${item.single.year}"
        }

        is ArtistItemUISealed.AlbumSongDetails -> {}
        is ArtistItemUISealed.PlaylistDetails -> {}
        is ArtistItemUISealed.ArtistDetailAlbum -> {
            ""
        }
        is ArtistItemUISealed.ArtistSongsList -> {
            ""
        }
    }
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable {
                when (item) {
                    is ArtistItemUISealed.Album -> {
                        onItemCLick.invoke(item.album.id.toString())
                    }

                    is ArtistItemUISealed.Singles -> {
                        onItemCLick.invoke(item.single.id.toString())
                    }

                    is ArtistItemUISealed.Song -> {
                        onItemCLick.invoke(item.song.id.toString())
                    }

                    is ArtistItemUISealed.AlbumSongDetails -> {
                        onItemCLick.invoke(item.albumSongDetails.id.toString())
                    }
                    is ArtistItemUISealed.PlaylistDetails -> {
                        onItemCLick.invoke(item.playlistDetails.id.toString())
                    }

                    is ArtistItemUISealed.ArtistDetailAlbum -> {
                        onItemCLick.invoke(item.albumList.id)
                    }
                    is ArtistItemUISealed.ArtistSongsList -> {
                        onItemCLick.invoke(item.songList.id)
                    }
                }

            },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)
        ) {
            Text(
                when (item) {
                    is ArtistItemUISealed.Album -> {
                        "${item.position}."
                    }

                    is ArtistItemUISealed.Singles -> {
                        "${item.position}."
                    }

                    is ArtistItemUISealed.Song -> {
                        "${item.position}."
                    }

                    is ArtistItemUISealed.AlbumSongDetails -> {
                        ""
                    }
                    is ArtistItemUISealed.PlaylistDetails -> {
                        ""
                    }
                    is ArtistItemUISealed.ArtistDetailAlbum -> {
                        "${item.position}."
                    }
                    is ArtistItemUISealed.ArtistSongsList -> {
                        "${item.position}."
                    }
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    when (item) {
                        is ArtistItemUISealed.Album -> {
                            item.album.image?.last()?.url
                        }

                        is ArtistItemUISealed.Song -> {
                            item.song.image?.last()?.url
                        }

                        is ArtistItemUISealed.Singles -> {
                            item.single.image?.last()?.url
                        }

                        is ArtistItemUISealed.AlbumSongDetails -> {
                            item.albumSongDetails.image?.last()?.url
                        }
                        is ArtistItemUISealed.PlaylistDetails -> {
                            item.playlistDetails.image?.last()?.url
                        }
                        is ArtistItemUISealed.ArtistDetailAlbum -> {
                            item.albumList.image.last().url
                        }
                        is ArtistItemUISealed.ArtistSongsList -> {
                            item.songList.image.last().url
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
                    .fillMaxWidth()
                    .padding(start = 5.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    when (item) {
                        is ArtistItemUISealed.Album -> {

                            item.album.name?.substringBefore("(")?.trim()
                        }

                        is ArtistItemUISealed.Song -> {
                            item.song.name?.substringBefore("(")?.trim()

                        }

                        is ArtistItemUISealed.Singles -> {
                            item.single.name?.substringBefore("(")?.trim()
                        }

                        is ArtistItemUISealed.AlbumSongDetails -> {
                            item.albumSongDetails.name
                        }
                        is ArtistItemUISealed.PlaylistDetails -> {
                            item.playlistDetails.name
                        }
                        is ArtistItemUISealed.ArtistDetailAlbum -> {
                            item.albumList.name.substringBefore("(").trim()
                        }
                        is ArtistItemUISealed.ArtistSongsList -> {
                            item.songList.name.substringBefore("(").trim()
                        }
                    }.toString(),
                    style = TextStyle(
                        fontSize = 16.sp, color = Color.Black,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(top = 5.dp),
                    maxLines = 1
                )
                Text(
                    when (item) {
                        is ArtistItemUISealed.Album -> {
                            albumDesc
                        }

                        is ArtistItemUISealed.Song -> {
                            songDesc
                        }

                        is ArtistItemUISealed.Singles -> {
                            singleSongDesc
                        }

                        is ArtistItemUISealed.AlbumSongDetails -> {
                            "${item.albumSongDetails.label} | ${formatNumber(item.albumSongDetails.playCount?.toLong()!!)}"
                        }
                        is ArtistItemUISealed.PlaylistDetails -> {
                            "${item.playlistDetails.label} | ${formatNumber(item.playlistDetails.playCount?.toLong()!!)}"
                        }
                        is ArtistItemUISealed.ArtistDetailAlbum -> {
                            "Total Songs : ${item.albumList.songCount}"
                        }
                        is ArtistItemUISealed.ArtistSongsList -> {
                            "${item.songList.label} )}"
                        }
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
        }
    }
}

