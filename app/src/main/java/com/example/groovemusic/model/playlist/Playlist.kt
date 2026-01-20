package com.example.groovemusic.model.playlist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean
)