package com.example.groovemusic.model.album_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageXX(
    @SerialName("quality")
    val quality: String? = null,
    @SerialName("url")
    val url: String? = null
)