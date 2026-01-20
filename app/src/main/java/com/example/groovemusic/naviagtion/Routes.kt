package com.example.groovemusic.naviagtion

import android.net.Uri
import com.example.groovemusic.model.song.Song
import com.example.groovemusic.model.storage.Favourite
import kotlinx.serialization.json.Json

sealed class Routes(val route : String) {
    object SplashScreen : Routes("splash_screen")
    object HomeScreen : Routes("home_screen")
    object PlayingScreen : Routes("playing_screen/{song_id}/{from_which_screen}") {

        fun createRoute(songId: String,fromWhichScreen: String) = "playing_screen/$songId/$fromWhichScreen"


    }
    object ArtistScreen : Routes("artist_screen/{artist_id}"){
        fun createRoute(artistId: String) = "artist_screen/$artistId"

    }

    object AlbumScreen : Routes("album_screen/{alubum_id}"){
        fun createRoute(albumId: String) = "album_screen/$albumId"

    }
    object PlayListDetails : Routes("playlist_details_screen/{playlist_id}"){
        fun createRoute(playListId: String) = "playlist_details_screen/$playListId"

    }
    object SearchScreen : Routes("search_screen")
    object FavouriteScreen : Routes("favourite_screen")
        object AddPlaylistScreen : Routes("add_playlist_screen?song_details_to_add_playlist={song_details_to_add_playlist}"){
            fun createRouteToPlayList(favourite: Favourite): String{
                val json = Json.encodeToString(favourite)
                val encoded = Uri.encode(json)
                return "add_playlist_screen?song_details_to_add_playlist=$encoded"

            }
            fun createEmptyRoute(): String {
                return "add_playlist_screen"
            }
        }
    object PlayListDetailScreen : Routes("playlist_detail_screen/{playlist_id}/{playlist_name}"){
        fun createRouteToPlaylistDetailScreen(playlistId: String, playlistName: String): String{
//            val json = Json.encodeToString(playlistSongs)
//            val encoded = Uri.encode(json)
            return "playlist_detail_screen/$playlistId/$playlistName"

        }
    }
    object ArtistDetailScreen : Routes("album_screen/{artist_id}/{fetch_Songs}/{artist_name}"){
        fun createRoute(artistId: String, fetchSong:Boolean,artistName: String) = "album_screen/$artistId/$fetchSong/$artistName"

    }

}