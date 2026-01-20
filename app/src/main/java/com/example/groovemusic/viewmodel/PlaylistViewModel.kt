package com.example.groovemusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.model.playlist.Playlist
import com.example.groovemusic.model.playlist_details.PlaylistDetails
import com.example.groovemusic.repositoryInterface.MusicInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(val musicInterface: MusicInterface) : ViewModel() {
    private val _playList = MutableStateFlow<Results<Playlist>>(Results.Loading)
    val playList: StateFlow<Results<Playlist>> = _playList

    private val _playListDetails = MutableStateFlow<Results<PlaylistDetails>>(Results.Loading)
    val playListDetails: StateFlow<Results<PlaylistDetails>> = _playListDetails

    fun getPlayList(query: String) {
        viewModelScope.launch {
            _playList.emit(musicInterface.getDefaultPlayList(query))
        }
    }

    fun getPlayListDetails(playListId: String) {
        viewModelScope.launch {
            _playListDetails.emit(musicInterface.getPlaylistDetails(playListId))
        }
    }
}