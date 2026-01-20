package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null
)