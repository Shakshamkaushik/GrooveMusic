package com.example.groovemusic.reporistoryImpl

import android.util.Log
import com.example.groovemusic.apiservice.KtorClient
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
import com.example.groovemusic.repositoryInterface.MusicInterface
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SongsImpl(val ktorClient: KtorClient) : MusicInterface {
    private var artistListCache: Results<ArtistsResponse>? = null
    private var songsListCache: Results<Song>? = null

    private var searchCache: Results<GlobalSearch>? = null
    override suspend fun getDefaultArtistList(query: String): Results<ArtistsResponse> {
        artistListCache?.let { return it }
        return try {
            val response: ArtistsResponse = ktorClient.apiClient
                .get("search/artists") {
                    parameter("query", if (query.isEmpty()) "%20" else query)
                    parameter("page", 0)
                    parameter("limit", 15)
                }.body()

            Results.Success(response).also {
                artistListCache = it
            }
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong !")
        }
    }

    override suspend fun getDefaultSongList(query: String): Results<Song> {
        songsListCache?.let { return it }
        return try {
            val response: Song = ktorClient.apiClient
                .get("search/songs") {
                    parameter("query", if (query.isEmpty()) "%20" else query)
                    parameter("page", 0)
                    parameter("limit", 50)
                }
                .body()

            Results.Success(response).also {
                songsListCache = it
            }

        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something went wrong")
        }
    }

    override suspend fun getDefaultAlbumList(query: String): Results<Album> {
        return try {
            val response: Album = ktorClient.apiClient.get("search/albums") {
                parameter("query", if (query.isEmpty()) "%20" else query)
                parameter("page", 0)
                parameter("limit", 15)
            }.body()

            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }

    }

    override suspend fun getDefaultPlayList(query: String): Results<Playlist> {

        return try {
            val response: Playlist = ktorClient.apiClient.get("search/playlists") {
                parameter("query", if (query.isEmpty()) "%50" else query)
                parameter("page", 0)
                parameter("limit", 15)
            }.body()

            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }
    }


    override suspend fun getSongDetails(songId: String): SingleSongItem {
        return ktorClient.apiClient.get("songs?ids=$songId").body()
    }

    override suspend fun getArtistDetails(artistId: String): Results<ArtistDetails> {
        return try {
            val response: ArtistDetails = ktorClient.apiClient.get("artists") {
                parameter("id", artistId.toInt())
            }.body()

            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }
    }


    override suspend fun getAlbumDetails(albumID: String): Results<AlbumDetails> {
        return try {
            val response: AlbumDetails = ktorClient.apiClient.get("albums") {
                parameter("id", albumID.toInt())
            }.body()

            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }
    }

    override suspend fun getPlaylistDetails(playlistId: String): Results<PlaylistDetails> {
        return try {
            val response: PlaylistDetails = ktorClient.apiClient.get("playlists") {
                parameter("id", playlistId.toInt())
                parameter("limit", 20)
            }.body()

            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }
    }

    override suspend fun getGlobalSearch(query: String): Results<GlobalSearch> {
        //  searchCache?.let { return it }
        return try {
            val response: GlobalSearch = ktorClient.apiClient.get("search") {
                parameter("query", query)
            }.body()


            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message ?: "Something Went Wrong")
        }
    }

    override suspend fun getArtistAlbumsList(
        artistId: String,
        page: Int
    ): Results<ArtistAlbumList> {
        return try {
            val response: ArtistAlbumList = ktorClient.apiClient.get("artists/$artistId/albums") {
                parameter("page", page)
            }.body()
            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message.toString())
        }

    }

    override suspend fun getArtistSongsList(
        artistId: String,
        page: Int
    ): Results<ArtistSongsList> {
        return try {
            val response: ArtistSongsList = ktorClient.apiClient.get("artists/$artistId/songs") {
                parameter("page", page)
            }.body()
            Results.Success(response)
        } catch (e: Exception) {
            Results.Failure(e.message.toString())
        }
    }
}