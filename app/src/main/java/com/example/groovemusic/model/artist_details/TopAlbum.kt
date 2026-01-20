package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAlbum(
    @SerialName("artists")
    val artists: ArtistsX?,
    @SerialName("description")
    val description: String?,
    @SerialName("explicitContent")
    val explicitContent: Boolean?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: List<Image>?,
    @SerialName("language")
    val language: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("playCount")
    val playCount: String?,
    @SerialName("songCount")
    val songCount: Int?,
    @SerialName("songs")
    val songs: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("year")
    val year: Int?
)