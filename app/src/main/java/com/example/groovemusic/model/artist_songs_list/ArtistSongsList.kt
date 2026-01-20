package com.example.groovemusic.model.artist_songs_list

import android.os.Parcelable
import com.example.groovemusic.model.song.Data
import com.example.groovemusic.model.song.SongResult
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ArtistSongsList(

    @SerialName("data")
    val data: ArtistSongsListData,
    @SerialName("success")
    val success: Boolean
) : Parcelable


@Parcelize
@Serializable
data class ArtistSongsListData(

    @SerialName("total")
    val total: Int,
    @SerialName("songs")
    val songs: List<SongResult>,
) : Parcelable

