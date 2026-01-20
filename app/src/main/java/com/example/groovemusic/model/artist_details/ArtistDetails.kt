package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDetails(
    @SerialName("data")
    val `data`: Data?,
    @SerialName("success")
    val success: Boolean?
)