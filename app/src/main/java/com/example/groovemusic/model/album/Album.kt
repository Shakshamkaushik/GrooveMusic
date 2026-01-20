package com.example.groovemusic.model.album


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean
)