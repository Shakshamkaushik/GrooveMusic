package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumX(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("url")
    val url: String?
)