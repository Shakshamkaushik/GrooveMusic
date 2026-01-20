package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllXX(
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: List<Image>?,
    @SerialName("name")
    val name: String?,
    @SerialName("role")
    val role: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?
)