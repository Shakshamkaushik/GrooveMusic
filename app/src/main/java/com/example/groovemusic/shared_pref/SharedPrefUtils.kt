package com.example.groovemusic.shared_pref

import android.content.Context
import android.util.Log
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.model.storage.PlayLists
import kotlinx.serialization.json.Json

object SharedPrefUtils {
    private val json = Json { ignoreUnknownKeys = true
        encodeDefaults = true}
    private const val PREF_NAME = "song_ids_list"
    private const val KEY_FAVOURITES = "favourite_songs_list"
    private const val KEY_PLAYLISTS = "playlist"
    private const val KEY_SONG_IDS = "song_ids"
    fun getSongsIds(context: Context): List<String>{
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_SONG_IDS, emptySet())?.toList() ?: emptyList()
    }

    fun saveId(context: Context, songId: String){
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val ids = prefs
            .getStringSet(KEY_SONG_IDS, emptySet())
            ?.toMutableSet()
            ?: mutableSetOf()


        if (ids.add(songId)) {
            prefs.edit()
                .putStringSet(KEY_SONG_IDS, ids)
                .apply()
        }
    }
    fun getFavouriteSongs(context: Context): MutableList<Favourite> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_FAVOURITES, null)
            ?: return mutableListOf()

        return json.decodeFromString(jsonString)
    }
    fun saveFavourite(context: Context, favourite: Favourite) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favourites = getFavouriteSongs(context)

        if (favourites.none { it.favouriteSongID == favourite.favouriteSongID }) {
            favourites.add(favourite)
            prefs.edit()
                .putString(KEY_FAVOURITES, json.encodeToString(favourites))
                .apply()
        }
    }
    fun removeFavourite(context: Context, songId: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favourites = getFavouriteSongs(context)

        val updatedList = favourites.filterNot {
            it.favouriteSongID == songId
        }

        prefs.edit()
            .putString(KEY_FAVOURITES, json.encodeToString(updatedList))
            .apply()
    }
                          // PlayList Code
    fun getPlaylists(context: Context): MutableList<PlayLists> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_PLAYLISTS, null)
            ?: return mutableListOf()

        return json.decodeFromString(jsonString)
    }

    private fun savePlaylists(context: Context, playlists: List<PlayLists>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_PLAYLISTS, json.encodeToString(playlists))
            .apply()
    }

    fun createPlaylist(
        context: Context,
        playlistId: String,
        playlistName: String
    ) {
        val playlists = getPlaylists(context)

        if (playlists.none { it.playlistId == playlistId }) {
            playlists.add(
                PlayLists(
                    playlistId = playlistId,
                    playlistName = playlistName
                )
            )
            savePlaylists(context, playlists)
        }
    }

    fun deletePlaylist(context: Context, playlistId: String) {
        val updated = getPlaylists(context).filterNot {
            it.playlistId == playlistId
        }
        savePlaylists(context, updated)
    }

    fun addSongToPlaylist(
        context: Context,
        playlistId: String,
        favourite: Favourite
    ) {
        val playlists = getPlaylists(context)
        val playlist = playlists.find { it.playlistId == playlistId } ?: return

        if (playlist.playListSongs.none { it.favouriteSongID == favourite.favouriteSongID }) {
            playlist.playListSongs.add(favourite)
            savePlaylists(context, playlists)
        }
    }

    fun removeSongFromPlaylist(
        context: Context,
        playlistId: String,
        songId: String
    ) {
        val playlists = getPlaylists(context)

        val index = playlists.indexOfFirst { it.playlistId == playlistId }
        if (index != -1) {
            playlists[index].playListSongs.removeAll {
                it.favouriteSongID == songId
            }
            savePlaylists(context, playlists)
        }
    }

}