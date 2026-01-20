package com.example.groovemusic.model.storage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Favourite(
    val favouriteSongName: String? = null,
    val favouriteSongDesc: String? = null,
    val favouriteSongArtist: String? = null,
    val favouriteSongAlbum: String? = null,
    val favouriteSongImage: String? = null,
    val favouriteSongID: String? = null,
    val playlistId: String? = null
): Parcelable
