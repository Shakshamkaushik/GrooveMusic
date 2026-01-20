package com.example.groovemusic.sealedclass


import com.example.groovemusic.model.album.AlbumResult
import com.example.groovemusic.model.artist.ArtistResult
import com.example.groovemusic.model.playlist.PlaylistResult
import com.example.groovemusic.model.song.SongResult

sealed class HomeScreenUISealed{
    data class HomeScreenSong(val songResult : SongResult): HomeScreenUISealed()
    data class HomeScreenAlbum(val albumResult : AlbumResult): HomeScreenUISealed()
    data class HomeScreenArtist(val artistResult : ArtistResult): HomeScreenUISealed()
    data class HomeScreenSongPlaylist(val playlistResult : PlaylistResult): HomeScreenUISealed()
}