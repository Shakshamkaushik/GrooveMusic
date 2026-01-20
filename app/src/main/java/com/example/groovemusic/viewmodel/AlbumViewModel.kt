package com.example.groovemusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovemusic.model.album.Album
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.repositoryInterface.MusicInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel(val musicInterface: MusicInterface): ViewModel() {
    private val _albumList = MutableStateFlow<Results<Album>>(Results.Loading)
    val albumList: StateFlow<Results<Album>> = _albumList

    fun getAlbum(query: String){
        viewModelScope.launch {
            _albumList.emit(musicInterface.getDefaultAlbumList(query))
        }
    }
}