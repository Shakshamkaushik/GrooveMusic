package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bio(
    @SerialName("sequence")
    val sequence: Int?,
    @SerialName("text")
    val text: String?,
    @SerialName("title")
    val title: String?
)