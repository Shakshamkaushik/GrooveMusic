package com.example.groovemusic.sealedclass

import com.example.groovemusic.model.album.AlbumResult
import com.example.groovemusic.model.artist.ArtistResult
import com.example.groovemusic.model.global_search.GlobalSearchResponse
import com.example.groovemusic.model.global_search.Result
import com.example.groovemusic.model.global_search.ResultX
import com.example.groovemusic.model.global_search.ResultXX
import com.example.groovemusic.model.global_search.ResultXXX
import com.example.groovemusic.model.song.SongResult
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.model.storage.PlayLists

sealed class SearchScreenUISealed(){
    data class SearchScreenSong(val globalSongs : ResultXXX): SearchScreenUISealed()
    data class SearchScreenAlbum(val globalArtist : Result): SearchScreenUISealed()
    data class SearchScreenArtist(val globalAlbum : ResultX): SearchScreenUISealed()
    data class SearchScreenPlaylist(val globalPlaylist : ResultXX): SearchScreenUISealed()
    data class PlayListSongs(val playlist : Favourite): SearchScreenUISealed()

}