package com.example.groovemusic.model.singlesong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageXX(
    @SerialName("quality")
    val quality: String,
    @SerialName("url")
    val url: String
)