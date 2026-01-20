package com.example.groovemusic.model.album_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("artists")
    val artists: Artists? = Artists(),
    @SerialName("description")
    val description: String? = "",
    @SerialName("explicitContent")
    val explicitContent: Boolean? = false,
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: List<ImageXX>? = listOf(),
    @SerialName("language")
    val language: String? = "",
    @SerialName("name")
    val name: String? = "",
//    @SerialName("playCount")
//    val playCount: Any? = Any(),
    @SerialName("songCount")
    val songCount: Int? = 0,
    @SerialName("songs")
    val songs: List<Song>? = listOf(),
    @SerialName("type")
    val type: String? = "",
    @SerialName("url")
    val url: String? = "",
    @SerialName("year")
    val year: Int? = 0
)