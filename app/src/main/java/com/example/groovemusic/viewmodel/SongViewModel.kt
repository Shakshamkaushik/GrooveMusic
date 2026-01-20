package com.example.groovemusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.model.global_search.GlobalSearch
import com.example.groovemusic.model.singlesong.SingleSongItem
import com.example.groovemusic.model.song.Song
import com.example.groovemusic.repositoryInterface.MusicInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongViewModel(val repo: MusicInterface) : ViewModel() {

    private val _songsList = MutableStateFlow<Results<Song>>(Results.Loading)
    val songsList: StateFlow<Results<Song>> = _songsList


    private val _songsDetails = MutableStateFlow<SingleSongItem?>(null)
    val songsDetails: StateFlow<SingleSongItem?> = _songsDetails

    private val _globalSongs = MutableStateFlow<Results<GlobalSearch?>>(Results.Loading)
    val globalSongs: StateFlow<Results<GlobalSearch?>> = _globalSongs


    fun getSongsList(query: String) {
        viewModelScope.launch {
            _songsList.emit(Results.Loading)
            _songsList.emit(repo.getDefaultSongList(query))
        }
    }

    fun getSongDetails(songId: String){
        viewModelScope.launch {
            _songsDetails.value = repo.getSongDetails(songId)
            _songsDetails.replayCache
        }
    }

    fun getGlobalSearch(query : String){
        viewModelScope.launch {
            _globalSongs.value = Results.Loading
            val response = repo.getGlobalSearch(query)
            _globalSongs.emit(response)

        }
    }

}