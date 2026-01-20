package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultXXX(
    @SerialName("album")
    val album: String? = "",
    @SerialName("description")
    val description: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: List<Image>? = listOf(),
    @SerialName("language")
    val language: String? = "",
    @SerialName("primaryArtists")
    val primaryArtists: String? = "",
    @SerialName("singers")
    val singers: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("type")
    val type: String? = "",
    @SerialName("url")
    val url: String? = ""
)