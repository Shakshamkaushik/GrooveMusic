package com.example.groovemusic.model.storage

import kotlinx.serialization.Serializable


@Serializable
data class PlayLists(
    val playlistId : String?= null,
    val playlistName : String? = null,
    val playlistImage : String?= null,
    val playListSongs: MutableList<Favourite> = mutableListOf()
)
