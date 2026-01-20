package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class SongResult(
    @SerialName("album")
    val album: Album,
    @SerialName("artists")
    val artists: Artists,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("downloadUrl")
    val downloadUrl: List<DownloadUrl>,
    @SerialName("duration")
    val duration: Int,
    @SerialName("explicitContent")
    val explicitContent: Boolean,
    @SerialName("hasLyrics")
    val hasLyrics: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: List<ImageXX>,
    @SerialName("label")
    val label: String,
    @SerialName("language")
    val language: String,
//    @SerialName("lyricsId")
//    val lyricsId: Any,
    @SerialName("name")
    val name: String,
    @SerialName("playCount")
    val playCount: Int?= null,
//    @SerialName("releaseDate")
//    val releaseDate: Any,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("year")
    val year: String
) : Parcelable