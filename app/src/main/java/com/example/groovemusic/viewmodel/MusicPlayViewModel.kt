package com.example.groovemusic.viewmodel

import android.annotation.SuppressLint
import android.content.Context

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.model.storage.PlayLists
import com.example.groovemusic.reporistoryImpl.PlaybackRepository
import com.example.groovemusic.sealedclass.PlaybackCommand
import com.example.groovemusic.shared_pref.SharedPrefUtils
import com.example.groovemusic.utils.NumberUtils.formatNumber
import com.example.groovemusic.utils.PlayerManger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MusicPlayViewModel(
    val playerManger: PlayerManger,
    private val playbackRepository: PlaybackRepository,
    private val songViewModel: SongViewModel,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {


    val isPlaying: StateFlow<Boolean> =
        playerManger.isPlayingFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                false
            )


    val songDuration: StateFlow<String> =
        playerManger.durationFlow
            .map { formatTime(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, "00:00")

    var popularSongsIds = ArrayList<String>()
    var previousPlayedSongId = ArrayList<String>()
    var albumSongList = ArrayList<String>()
    var playListSongList = ArrayList<String>()
    var artistSongList = ArrayList<String>()
    var playListSongIds = ArrayList<String>()
    var favouriteSongIds = ArrayList<String>()
    var _repeatSong = MutableStateFlow(false)
    val repeatSong: StateFlow<Boolean> = _repeatSong

    private val _playLists = MutableStateFlow<List<PlayLists>>(emptyList())
    val playLists: StateFlow<List<PlayLists>> = _playLists.asStateFlow()

    private val _currentPlaylistSongs = MutableStateFlow<List<Favourite>>(emptyList())
    val currentPlaylistSongs: StateFlow<List<Favourite>> = _currentPlaylistSongs.asStateFlow()

    private val _favouriteSongsList = MutableStateFlow<List<Favourite>>(emptyList())
    val favouriteSongsList: StateFlow<List<Favourite>> = _favouriteSongsList.asStateFlow()

    private val _currentPlayingSongId = MutableStateFlow("")
    val currentPlayingSongId: StateFlow<String> = _currentPlayingSongId.asStateFlow()

    private val _lastPlayedUrl = MutableStateFlow("")
    val lastPlayedUrl: StateFlow<String> = _lastPlayedUrl.asStateFlow()

    var lastRouteFromWhichScreen = ""
    var lastSongId = ""
    var searchText by mutableStateOf("")


    val currentPosition: StateFlow<String> =
        playerManger.positionFlow
            .map { formatTime(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, "00:00")


    val isCompleted = combine(playerManger.positionFlow, playerManger.durationFlow) { pos, dur ->
        dur > 0 && pos >= dur
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        viewModelScope.launch {
            isCompleted
                .filter { it }.collect { comleted ->

                     if (!repeatSong.value){
                    if (comleted) {
                        when (lastRouteFromWhichScreen) {
                            "Album_Screen" -> {
                                songViewModel.getSongDetails(albumSongList.random())
                            }

                            "Artist_Screen" -> {
                                songViewModel.getSongDetails(artistSongList.random())
                            }

                            "Home_Screen" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }
                            "Artist_Album_Detail_Screen" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }

                            "Playlist_Screen" -> {
                                songViewModel.getSongDetails(playListSongList.random())
                            }

                            "Playlist_Screen_Single_Track" -> {
                                songViewModel.getSongDetails(albumSongList.random())
                            }

                            "Album_Screen_Single_Track" -> {
                                songViewModel.getSongDetails(albumSongList.random())
                            }

                            "Favourite_Screen" -> {
                                val favouriteSongId = favouriteSongIds
                                    .filter { it != currentPlayingSongId.value }
                                    .randomOrNull()
                                    .toString()
                                songViewModel.getSongDetails(favouriteSongId)
                            }

                            "PlayList_Detail_Screen" -> {
                                val playListSongId = playListSongIds
                                    .filter { it != currentPlayingSongId.value }
                                    .randomOrNull()
                                    .toString()
                                songViewModel.getSongDetails(playListSongId)
                            }
                        }
                    }
                    }else {
                        playerManger.play()
                    }

                }
        }
        viewModelScope.launch {
            playbackRepository.commands.collect { command ->
                when (command) {
                    PlaybackCommand.Next -> {
                        when (lastRouteFromWhichScreen) {
                            "Album_Screen" -> {
                                songViewModel.getSongDetails(albumSongList.random())
                            }

                            "Artist_Screen" -> {
                                songViewModel.getSongDetails(artistSongList.random())
                            }

                            "Home_Screen" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }
                            "Artist_Album_Detail_Screen" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }

                            "Playlist_Screen" -> {
                                songViewModel.getSongDetails(playListSongList.random())
                            }

                            "Playlist_Screen_Single_Track" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }

                            "Album_Screen_Single_Track" -> {
                                songViewModel.getSongDetails(popularSongsIds.random())
                            }

                            "Favourite_Screen" -> {
                                val favouriteSongId = favouriteSongIds
                                    .filter { it != currentPlayingSongId.value }
                                    .randomOrNull()
                                    .toString()
                                songViewModel.getSongDetails(favouriteSongId)
                            }

                            "PlayList_Detail_Screen" -> {
                                val playListSongId = playListSongIds
                                    .filter { it != currentPlayingSongId.value }
                                    .randomOrNull()
                                    .toString()
                                songViewModel.getSongDetails(playListSongId)
                            }
                        }

                    }

                    PlaybackCommand.Previous -> {

                        when (lastRouteFromWhichScreen) {

                            "Album_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )
                                    songViewModel.getSongDetails(previousPlayedSongId.random())
                                }
                            }

                            "Artist_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )
                                    songViewModel.getSongDetails(previousPlayedSongId.random())
                                }
                            }

                            "Home_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )
                                    songViewModel.getSongDetails(previousPlayedSongId.random())
                                }
                            }
                            "Artist_Album_Detail_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )
                                    songViewModel.getSongDetails(previousPlayedSongId.random())
                                }
                            }

                            "Playlist_Screen" -> {

                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )

                                     songViewModel.getSongDetails(previousPlayedSongId.last())
                                }
                            }

                            "Favourite_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )

                                    songViewModel.getSongDetails(previousPlayedSongId.last())
                                }
                            }

                            "PlayList_Detail_Screen" -> {
                                if (previousPlayedSongId.size > 1) {
                                    previousPlayedSongId.removeAt(
                                        previousPlayedSongId.size - 1
                                    )
                                    songViewModel.getSongDetails(previousPlayedSongId.last())
                                }
                            }

                            "Playlist_Screen_Single_Track" -> {}
                            "Album_Screen_Single_Track" -> {}
                        }

                    }
                }

            }
        }

        viewModelScope.launch {
            songViewModel.songsDetails
                .filterNotNull()
                .collect { song ->
                    val trackUrl =
                        song.data.lastOrNull()?.downloadUrl?.lastOrNull()?.url ?: return@collect
                    val songName = song.data.lastOrNull()?.name?.substringBefore("(")?.trim().toString()
                    val artworkUrl = song.data.lastOrNull()?.image?.lastOrNull()?.url ?: ""
                    val playCountNumber = song.data.lastOrNull()?.playCount?.toLong()
                    val songId = song.data.lastOrNull()?.id.toString()
                    val artistName =
                        song.data.lastOrNull()?.artists?.primary?.first()?.name.toString()


                    val playCountFormatted = playCountNumber?.let {
                        formatNumber(it)
                    }

                    val desc =
                        "$playCountFormatted | ${song.data.lastOrNull()?.year} | ${song.data.lastOrNull()?.copyright})"

                    sharedViewModel.setSong(
                        SongDeetails(
                            name = songName,
                            desc = desc,
                            image = artworkUrl,
                            artist = artistName
                        )
                    )
                    setLastPlayedUrl(trackUrl)
                    setCurrentPLayingSong(songId)
                    markSongFetched(songId)
                    playSong(songId, trackUrl, songName, artworkUrl, artistName)
                }
        }
    }

    fun setCurrentPLayingSong(songId: String) {

        _currentPlayingSongId.value = songId
    }

    fun markSongFetched(songId: String) {
        lastSongId = songId
    }

    fun repeatSong() {
        _repeatSong.value = !_repeatSong.value
    }

    fun setLastPlayedUrl(trackUrl: String) {
        _lastPlayedUrl.value = trackUrl
    }

    fun playSong(songId: String, track: String, name: String, url: String, artistName: String) {

        viewModelScope.launch {

            if (previousPlayedSongId.isEmpty()) {
                previousPlayedSongId.add(songId)

            } else {
                val last = previousPlayedSongId.last()
                if (last != songId) {
                    previousPlayedSongId.add(songId)

                }
            }

            playerManger.playSong(track, name, url, artistName)
        }
    }

    fun loadPopularSongs(context: Context) {
        popularSongsIds = ArrayList(SharedPrefUtils.getSongsIds(context))
    }

    fun saveSongId(context: Context, id: String) {
        SharedPrefUtils.saveId(context, id)
        loadPopularSongs(context)
    }

    fun loadFavouriteSongs(context: Context) {
        _favouriteSongsList.value = ArrayList(SharedPrefUtils.getFavouriteSongs(context))
        favouriteSongIds.clear()
        _favouriteSongsList.value.forEach { action ->
            favouriteSongIds.add(action.favouriteSongID.toString())
        }
    }

    fun removeFavouriteSongs(context: Context, songId: String) {
        SharedPrefUtils.removeFavourite(context, songId)
        loadFavouriteSongs(context)
    }

    fun saveFavouriteSongs(context: Context, favourite: Favourite) {
        SharedPrefUtils.saveFavourite(context, favourite)
        loadFavouriteSongs(context)
    }

    fun isFavourite(songId: String): Boolean {
        return _favouriteSongsList.value.any { it.favouriteSongID == songId }
    }

    fun toggleFavourite(context: Context, favourite: Favourite) {
        if (isFavourite(favourite.favouriteSongID.toString())) {
            removeFavouriteSongs(context, favourite.favouriteSongID.toString())
        } else {
            saveFavouriteSongs(context, favourite)
        }
    }

    fun playPauseClick() {
        playerManger.togglePlayPause()
    }



    fun seekToMusic(position: Int) {

        playerManger.seekTo(position)
    }


    @SuppressLint("DefaultLocale")
    private fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format("%02d:%02d", min, sec)
    }



    fun getAllPlayList(context: Context) {
        val playlists = SharedPrefUtils.getPlaylists(context)

        _playLists.value = playlists
    }


    fun createPlayList(context: Context, playListId: String, playListName: String) {
        SharedPrefUtils.createPlaylist(context, playListId, playListName)
        getAllPlayList(context)
    }

    fun addSongToPlayList(context: Context, playListId: String, songDetails: Favourite) {

        val isAlreadyExist = playLists.value.any { action ->
            action.playListSongs.any { it.favouriteSongID == songDetails.favouriteSongID }
        }
        if (!isAlreadyExist) {
            SharedPrefUtils.addSongToPlaylist(
                context,
                playlistId = playListId,
                favourite = songDetails
            )
            Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Already Added!", Toast.LENGTH_SHORT).show()
        }

        getAllPlayList(context)
    }

    fun removeSongFromPLayList(context: Context, playlistId: String, songId: String) {
        SharedPrefUtils.removeSongFromPlaylist(context, playlistId, songId)
        getAllPlayList(context)
        getSongsFromPlayList(playlistId)
    }

    fun getSongsFromPlayList(playlistId: String) {
        val playLists = playLists.value.firstOrNull { it.playlistId == playlistId }
        playListSongIds.clear()
        playLists?.playListSongs?.forEach { action ->
            playListSongIds.add(action.favouriteSongID.toString())
        }

        _currentPlaylistSongs.value = playLists?.playListSongs ?: emptyList()
    }

    fun deletePlayList(context: Context, playlistId: String) {
        SharedPrefUtils.deletePlaylist(context, playlistId)
        getAllPlayList(context)
    }

}
