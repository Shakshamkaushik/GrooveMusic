package com.example.groovemusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovemusic.model.album.AlbumResult
import com.example.groovemusic.model.album_details.AlbumDetails
import com.example.groovemusic.model.artist.ArtistsResponse
import com.example.groovemusic.model.artist_details.ArtistDetails
import com.example.groovemusic.model.artist_songs_list.ArtistAlbumList
import com.example.groovemusic.model.artist_songs_list.ArtistSongsList
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.model.song.SongResult
import com.example.groovemusic.repositoryInterface.MusicInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(val musicInterface: MusicInterface) : ViewModel() {
    private val _artistList = MutableStateFlow<Results<ArtistsResponse>>(Results.Loading)
    val artistList: StateFlow<Results<ArtistsResponse>> = _artistList

    private val _artistDetails = MutableStateFlow<Results<ArtistDetails>>(Results.Loading)
    val artistDetails: StateFlow<Results<ArtistDetails>> = _artistDetails

    private val _artistAlbumDetails = MutableStateFlow<Results<AlbumDetails>>(Results.Loading)
    val artistAlbumDetails: StateFlow<Results<AlbumDetails>> = _artistAlbumDetails

    private val _artistSongsList = MutableStateFlow<Results<ArtistSongsList>>(Results.Loading)
    val artistSongsList: StateFlow<Results<ArtistSongsList>> = _artistSongsList


    private val _artistAlbumList = MutableStateFlow<Results<ArtistAlbumList>>(Results.Loading)
    val artistAlbumList: StateFlow<Results<ArtistAlbumList>> = _artistAlbumList

    private val songsList = mutableListOf<SongResult>()
    private val albumList = mutableListOf<AlbumResult>()

    var songPage: Int = 0
    var albumPage: Int = 0
    private var isLoading = false


    fun getArtist(query: String) {
        viewModelScope.launch {
            _artistList.emit(musicInterface.getDefaultArtistList(query))
        }
    }

    fun getArtistDetails(artistId: String) {
        viewModelScope.launch {
            _artistDetails.emit(musicInterface.getArtistDetails(artistId))
        }
    }

    fun getArtistAlbumDetails(albumId: String) {
        viewModelScope.launch {
            _artistAlbumDetails.emit(musicInterface.getAlbumDetails(albumId))
        }
    }

    fun getArtistSongList(artistId: String) {
        viewModelScope.launch {
            val response: Results<ArtistSongsList> =
                musicInterface.getArtistSongsList(artistId, songPage)
            if (response is Results.Success) {
                songPage++
                val newsongs = response.data.data.songs
                if (newsongs.isEmpty()){
                    isLoading = false
                    return@launch
                }
                songsList.addAll(newsongs)

                 val newResponse=   response.data.copy(
                    data = response.data.data.copy(
                        songs = songsList.toList()
                    )
                )
                _artistSongsList.emit(Results.Success(newResponse))
            }
        }
    }
    fun getArtistAlbumList(artistId: String) {
        viewModelScope.launch {
            val response =
                musicInterface.getArtistAlbumsList(artistId, albumPage)
            if (response is Results.Success) {
                val newAlbum = response.data.data.albums
                if (newAlbum.isEmpty()){
                    isLoading = false
                    return@launch
                }
                albumList.addAll(newAlbum)
                albumPage++

                 val newResponse=   response.data.copy(
                    data = response.data.data.copy(
                        albums = albumList.toList()
                    )
                )
                _artistAlbumList.emit(Results.Success(newResponse))
            }
        }
    }
}