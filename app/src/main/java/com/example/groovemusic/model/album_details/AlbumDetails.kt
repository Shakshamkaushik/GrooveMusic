package com.example.groovemusic.model.album_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDetails(
    @SerialName("data")
    val `data`: Data? = Data(),
    @SerialName("success")
    val success: Boolean? = false
)