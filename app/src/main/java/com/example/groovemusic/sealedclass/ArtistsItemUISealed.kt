package com.example.groovemusic.sealedclass

import com.example.groovemusic.model.album.AlbumResult
import com.example.groovemusic.model.artist_details.Single
import com.example.groovemusic.model.artist_details.TopAlbum
import com.example.groovemusic.model.artist_details.TopSong
import com.example.groovemusic.model.song.SongResult

sealed class ArtistItemUISealed {
    data class Song(val song: TopSong, val position: Int) : ArtistItemUISealed()
    data class Album(val album: TopAlbum, val position: Int) : ArtistItemUISealed()
    data class Singles(val single: Single, val position: Int) : ArtistItemUISealed()
    data class AlbumSongDetails(val albumSongDetails: com.example.groovemusic.model.album_details.Song) : ArtistItemUISealed()

    data class PlaylistDetails(val playlistDetails: com.example.groovemusic.model.playlist_details.Song) : ArtistItemUISealed()
    data class ArtistSongsList(val songList: SongResult,val position: Int) : ArtistItemUISealed()
    data class ArtistDetailAlbum(val albumList: AlbumResult, val position: Int) : ArtistItemUISealed()
}