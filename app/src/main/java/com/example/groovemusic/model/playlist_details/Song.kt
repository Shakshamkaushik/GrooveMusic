package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    @SerialName("album")
    val album: Album? = Album(),
    @SerialName("artists")
    val artists: Artists? = Artists(),
    @SerialName("copyright")
    val copyright: String? = "",
    @SerialName("downloadUrl")
    val downloadUrl: List<DownloadUrl>? = listOf(),
    @SerialName("duration")
    val duration: Int? = 0,
    @SerialName("explicitContent")
    val explicitContent: Boolean? = false,
    @SerialName("hasLyrics")
    val hasLyrics: Boolean? = false,
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: List<ImageX>? = listOf(),
    @SerialName("label")
    val label: String? = "",
    @SerialName("language")
    val language: String? = "",
//    @SerialName("lyricsId")
//    val lyricsId: Any? = Any(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("playCount")
    val playCount: Int? = 0,
    @SerialName("releaseDate")
    val releaseDate: String? = "",
    @SerialName("type")
    val type: String? = "",
    @SerialName("url")
    val url: String? = "",
    @SerialName("year")
    val year: String? = ""
)