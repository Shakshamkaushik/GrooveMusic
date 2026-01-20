package com.example.groovemusic.repositoryInterface

import com.example.groovemusic.model.album.Album
import com.example.groovemusic.model.album_details.AlbumDetails
import com.example.groovemusic.model.artist.ArtistsResponse
import com.example.groovemusic.model.artist_details.ArtistDetails
import com.example.groovemusic.model.artist_songs_list.ArtistAlbumList
import com.example.groovemusic.model.artist_songs_list.ArtistSongsList
import com.example.groovemusic.model.baseclass.Results
import com.example.groovemusic.model.global_search.GlobalSearch
import com.example.groovemusic.model.playlist.Playlist
import com.example.groovemusic.model.playlist_details.PlaylistDetails
import com.example.groovemusic.model.singlesong.SingleSongItem
import com.example.groovemusic.model.song.Song

interface MusicInterface {
    suspend fun getDefaultArtistList(query: String): Results<ArtistsResponse>
    suspend fun getDefaultSongList(query: String): Results<Song>
    suspend fun getDefaultAlbumList(query: String): Results<Album>
    suspend fun getDefaultPlayList(query: String):  Results<Playlist>
    suspend fun getSongDetails(songId: String): SingleSongItem

    suspend fun getArtistDetails(artistId : String) : Results<ArtistDetails>
    suspend fun getAlbumDetails(albumID : String) : Results<AlbumDetails>

    suspend fun getPlaylistDetails(playlistId:String): Results<PlaylistDetails>

    suspend fun getGlobalSearch(query: String): Results<GlobalSearch>

    suspend fun getArtistAlbumsList(artistId: String, page:Int): Results<ArtistAlbumList>
    suspend fun getArtistSongsList(artistId: String, page:Int): Results<ArtistSongsList>
}