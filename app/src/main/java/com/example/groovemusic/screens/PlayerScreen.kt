package com.example.groovemusic.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeDefaults.Iterations
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.groovemusic.R
import com.example.groovemusic.model.singlesong.DownloadUrl
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.naviagtion.Routes
import com.example.groovemusic.utils.NumberUtils.formatNumber
import com.example.groovemusic.utils.NumberUtils.parseTimeToSeconds
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.SharedViewModel
import com.example.groovemusic.viewmodel.SongDeetails
import com.example.groovemusic.viewmodel.SongViewModel
import io.github.ningyuv.circularseekbar.CircularSeekbarView
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@SuppressLint("ResourceType")
@Composable
fun PlayerScreen(
    navController: NavHostController,
    songIdPlayed: String,
    fromWhichScreen: String,
    sharedViewModel: SharedViewModel,
    musicPlayViewModel: MusicPlayViewModel,
    songViewModel: SongViewModel
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()


    val songId by musicPlayViewModel.currentPlayingSongId.collectAsState()
    val from_Which_Screen by remember { mutableStateOf(fromWhichScreen) }
    val colorList = listOf(
        R.color.light_purple,
        R.color.light_blue,
        R.color.light_dark_blue,
        R.color.light_green
    )
    val isFavourite = musicPlayViewModel.isFavourite(songId)

    val songDetails by songViewModel.songsDetails.collectAsState()
    val repeatSong by musicPlayViewModel.repeatSong.collectAsState()


    val totalSongDuration by musicPlayViewModel.songDuration.collectAsState()
    val currSongDuration by musicPlayViewModel.currentPosition.collectAsState()


    val isPlaying by musicPlayViewModel.isPlaying.collectAsStateWithLifecycle()
    val lastPlayedUrl by musicPlayViewModel.lastPlayedUrl.collectAsStateWithLifecycle()
    val downloadList = remember { mutableListOf<DownloadUrl>() }
    var songName by remember { mutableStateOf("") }
    var songDesc by remember { mutableStateOf("") }
    var songImage by remember { mutableStateOf("") }


    val pickRandomColor = remember {
        colorList.random()
    }
    val sharedViewModelSongDetails by sharedViewModel.selectedSong.collectAsState()


    LaunchedEffect(songId) {

        if (songId.isNotEmpty() && musicPlayViewModel.lastSongId != songId) {

            songViewModel.getSongDetails(songId)

        } else {
            songName = sharedViewModelSongDetails?.name.toString()
            songDesc = sharedViewModelSongDetails?.desc.toString()
        }
    }

    LaunchedEffect(songDetails) {

        if (songId.isNotEmpty() && songId != musicPlayViewModel.lastSongId) {
            songDetails?.data?.firstOrNull()?.let { song ->
                val url = song.downloadUrl.lastOrNull()?.url

                if (!url.isNullOrEmpty() && url != lastPlayedUrl) {
                    musicPlayViewModel.setLastPlayedUrl(url)
                    musicPlayViewModel.playSong(
                        song.id, url, song.name, song.image.last().url,
                        song.artists.primary.first().name
                    )
                    musicPlayViewModel.markSongFetched(songId)
                    musicPlayViewModel.songDuration
                }
                downloadList.clear()
                song.downloadUrl.fastForEach {
                    downloadList.add(DownloadUrl(it.quality, it.url))
                }
                sharedViewModel.setSong(
                    SongDeetails(
                        name = song.name.substringBefore("(").trim(),
                        desc = "${formatNumber(song.playCount.toLong())} | ${song.year} | ${song.copyright}",
                        image = song.image.last().url,
                        artist = song.artists.primary.first().name,
                    )
                )

                songName = song.name.substringBefore("(").trim()
                songDesc =
                    "${formatNumber(song.playCount.toLong())} | ${song.year} | ${song.copyright}"
                songImage = song.image.last().url

            }
        }
    }
    LaunchedEffect(sharedViewModelSongDetails) {
        songName = sharedViewModelSongDetails?.name.toString()
        songDesc = sharedViewModelSongDetails?.desc.toString()
        songImage = sharedViewModelSongDetails?.image.toString()

    }



    val totalDurationTime = parseTimeToSeconds(totalSongDuration)
    val currentTime = parseTimeToSeconds(currSongDuration)
    
    
    val progressValue = remember(currentTime, totalDurationTime) {
        if (totalDurationTime == 0) 0f
        else currentTime.toFloat() / totalDurationTime.toFloat()
    }


    var showMenu by remember { mutableStateOf(false) }
    var songQuality by remember { mutableStateOf("320 Kbps") }

    Box(
        modifier = Modifier
            .background(color = colorResource(pickRandomColor))
            .fillMaxSize()
    ) {


        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(horizontal = 10.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Image(
                    painterResource(R.drawable.playlist_add),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            val songDetails = Favourite(
                                favouriteSongName = songName,
                                favouriteSongDesc = songDesc,
                                favouriteSongImage = songImage,
                                favouriteSongID = songId
                            )
                            navController.navigate(
                                Routes.AddPlaylistScreen.createRouteToPlayList(
                                    songDetails
                                )
                            ) {
                                popUpTo(Routes.HomeScreen.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }

                        }
                )
            }



            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color.White, shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)
                    )
                    .then(
                        if (isLandscape)
                            Modifier.verticalScroll(scrollState)
                        else
                            Modifier
                    )
            ) {
                Text(
                    "Song Quality - $songQuality",
                    style = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.Monospace),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f), 
                    contentAlignment = Alignment.Center
                ) {
                    CircularSeekbarView(
                        value = progressValue,
                        onChange = { newValue ->

                            val seekSec = (newValue * totalDurationTime).toInt()
                            musicPlayViewModel.seekToMusic(seekSec)

                        },
                        lineWeight = 5.dp,
                        dotRadius = 5.dp,
                        dotColor = colorResource(pickRandomColor),
                        inactiveColor = colorResource(pickRandomColor),
                        activeColor = colorResource(pickRandomColor),
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize()
                    )

                    Image(
                        painter = rememberAsyncImagePainter(songImage),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(0.85f)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        currSongDuration,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        "-", style = TextStyle(fontSize = 16.sp), modifier = Modifier
                    )
                    Text(
                        totalSongDuration,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Text(
                    songName, style = TextStyle(fontSize = 16.sp), modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(vertical = 10.dp)
                )
                Text(
                    songDesc, style = TextStyle(fontSize = 16.sp), modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(vertical = 10.dp, horizontal = 5.dp)
                        .basicMarquee(Iterations.minutes.toInt(DurationUnit.MINUTES))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.right_shift_icon),
                        contentDescription = "previous song",
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(180f)
                            .clickable {
                                when (from_Which_Screen) {
                                    "Playlist_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Album_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Artist_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Home_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Search_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Playlist_Screen_Single_Track" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "Album_Screen_Single_Track" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )

                                        }
                                    }

                                    "PlayList_Detail_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )
                                        }
                                    }

                                    "Favourite_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )
                                        }
                                    }
                                    "Artist_Album_Detail_Screen" -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )
                                        }
                                    }

                                    else -> {
                                        if (musicPlayViewModel.previousPlayedSongId.size > 1) {
                                            musicPlayViewModel.previousPlayedSongId.removeAt(
                                                musicPlayViewModel.previousPlayedSongId.size - 1
                                            )
                                            musicPlayViewModel.setCurrentPLayingSong(
                                                musicPlayViewModel.previousPlayedSongId.last()
                                            )
                                        }
                                    }
                                }
                            },
                        colorFilter = ColorFilter.tint(Color.LightGray)

                    )

                    Image(
                        if (isPlaying) painterResource(R.drawable.pause_icon) else painterResource(
                            R.drawable.play_icon
                        ),
                        contentDescription = "play / pause",
                        modifier = Modifier
                            .size(70.dp)
                            .clickable {
                                musicPlayViewModel.playPauseClick()
                            },
                        colorFilter = ColorFilter.tint(colorResource(pickRandomColor))
                    )
                    Image(
                        painterResource(R.drawable.right_shift_icon),
                        contentDescription = "next song",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                when (from_Which_Screen) {
                                    "Playlist_Screen" -> {
                                        val playListSongId = musicPlayViewModel.playListSongList
                                            .filter { it != songId }
                                            .randomOrNull()
                                        playListSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Album_Screen" -> {
                                        val albumSongId = musicPlayViewModel.albumSongList
                                            .filter { it != songId }
                                            .randomOrNull()
                                        albumSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Artist_Screen" -> {
                                        val artistSongId = musicPlayViewModel.artistSongList
                                            .filter { it != songId }
                                            .randomOrNull()
                                        artistSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Home_Screen" -> {
                                        val nextSongId = musicPlayViewModel.popularSongsIds
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }
                                    "Artist_Album_Detail_Screen" -> {
                                        val nextSongId = musicPlayViewModel.popularSongsIds
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Search_Screen" -> {
                                        val nextSongId = musicPlayViewModel.popularSongsIds
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Playlist_Screen_Single_Track" -> {
                                        val nextSongId = musicPlayViewModel.playListSongList
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Album_Screen_Single_Track" -> {
                                        val nextSongId = musicPlayViewModel.albumSongList
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "PlayList_Detail_Screen" -> {


                                        val nextSongId = musicPlayViewModel.playListSongIds
                                            .filter { it != songId }
                                            .randomOrNull()
                                        nextSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }

                                    "Favourite_Screen" -> {
                                        val favouriteSongId =
                                            musicPlayViewModel.favouriteSongIds
                                                .filter { it != songId }
                                                .randomOrNull()
                                        favouriteSongId?.let {
                                            musicPlayViewModel.setCurrentPLayingSong(it)
                                        }
                                    }
                                }
                            },
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                }


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(pickRandomColor),
                        shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)
                    ),

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Image(
                        painterResource(if (isFavourite) R.drawable.fav_filled else R.drawable.fav),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                val newFavourite = Favourite(
                                    favouriteSongName = songName,
                                    favouriteSongDesc = songDesc,
                                    favouriteSongImage = songImage,
                                    favouriteSongID = songId
                                )

                                musicPlayViewModel.toggleFavourite(context, newFavourite)

                            },

                    )
                    Image(
                        painterResource(if (repeatSong) R.drawable.repeat_loop else R.drawable.shuffle),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                musicPlayViewModel.repeatSong()
                            }
                    )
                    Box(
                        modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        Image(
                            painterResource(R.drawable.music_quality),
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    showMenu = !showMenu
                                })
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            downloadList.forEach { item ->
                                DropdownMenuItem(text = { Text(item.quality) }, onClick = {
                                    showMenu = false
                                })
                            }
                        }

                    }
                }
            }
        }
    }
}



