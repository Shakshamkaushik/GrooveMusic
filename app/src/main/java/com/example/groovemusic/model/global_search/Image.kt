package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("quality")
    val quality: String? = null,
    @SerialName("url")
    val url: String? = null
)