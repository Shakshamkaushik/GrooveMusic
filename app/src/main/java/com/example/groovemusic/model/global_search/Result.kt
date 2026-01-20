package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("artist")
    val artist: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("image")
    val image: List<Image?>? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("songIds")
    val songIds: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("year")
    val year: String? = null
)