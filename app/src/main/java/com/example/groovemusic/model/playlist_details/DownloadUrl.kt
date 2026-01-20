package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadUrl(
    @SerialName("quality")
    val quality: String? = null,
    @SerialName("url")
    val url: String? = null
)