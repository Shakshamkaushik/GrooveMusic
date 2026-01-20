package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Single(
    @SerialName("album")
    val album: Album?,
    @SerialName("artists")
    val artists: Artists?,
//    @SerialName("copyright")
//    val copyright: Any? = null,
//    @SerialName("downloadUrl")
//    val downloadUrl: List<Any?>?,
//    @SerialName("duration")
//    val duration: Any?,
    @SerialName("explicitContent")
    val explicitContent: Boolean?,
    @SerialName("hasLyrics")
    val hasLyrics: Boolean?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: List<Image>?,
//    @SerialName("label")
//    val label: Any?,
    @SerialName("language")
    val language: String?,
//    @SerialName("lyricsId")
//    val lyricsId: Any?,
    @SerialName("name")
    val name: String?,
    @SerialName("playCount")
    val playCount: String?,
    @SerialName("releaseDate")
    val releaseDate: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("year")
    val year: String?
)