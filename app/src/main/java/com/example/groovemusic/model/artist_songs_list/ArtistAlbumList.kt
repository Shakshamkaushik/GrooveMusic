package com.example.groovemusic.model.artist_songs_list

import android.os.Parcelable
import com.example.groovemusic.model.album.AlbumResult
import com.example.groovemusic.model.song.SongResult
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class ArtistAlbumList(

    @SerialName("data")
    val data: ArtistAlbumListData,
    @SerialName("success")
    val success: Boolean
) : Parcelable


@Parcelize
@Serializable
data class ArtistAlbumListData(

    @SerialName("total")
    val total: Int,
    @SerialName("albums")
    val albums: List<AlbumResult>,
) : Parcelable



