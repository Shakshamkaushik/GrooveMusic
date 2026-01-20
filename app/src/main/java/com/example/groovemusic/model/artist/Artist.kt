package com.example.groovemusic.model.artist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    @SerialName("data")
    val data: Data,
    @SerialName("success")
    val success: Boolean
)