package com.example.groovemusic.model.playlist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("quality")
    val quality: String,
    @SerialName("url")
    val url: String
)