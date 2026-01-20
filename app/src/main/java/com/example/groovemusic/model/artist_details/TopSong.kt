package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopSong(
    @SerialName("album")
    val album: AlbumX?,
    @SerialName("artists")
    val artists: ArtistsXX?,
    @SerialName("copyright")
    val copyright: String?,
    @SerialName("downloadUrl")
    val downloadUrl: List<DownloadUrl>?,
    @SerialName("duration")
    val duration: Int?,
    @SerialName("explicitContent")
    val explicitContent: Boolean?,
    @SerialName("hasLyrics")
    val hasLyrics: Boolean?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: List<Image>?,
    @SerialName("label")
    val label: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("lyricsId")
    val lyricsId: String?,
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