package com.example.groovemusic.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _selectedSong = MutableStateFlow<SongDeetails?>(null)
    val selectedSong: StateFlow<SongDeetails?> = _selectedSong

    fun setSong(song: SongDeetails) {
        _selectedSong.value = song
    }
}

data class SongDeetails(
    val name: String,
    val desc: String,
    val image: String,
    val artist: String
)