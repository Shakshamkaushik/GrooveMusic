package com.example.groovemusic.model.playlist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResult(
    @SerialName("explicitContent")
    val explicitContent: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: List<Image>,
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String,
    @SerialName("songCount")
    val songCount: Int,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)