package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Primary(
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: List<ImageX>? = listOf(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("role")
    val role: String? = "",
    @SerialName("type")
    val type: String? = "",
    @SerialName("url")
    val url: String? = ""
)