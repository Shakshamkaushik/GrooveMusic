package com.example.groovemusic.model.singlesong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleSongItem(
    @SerialName("data")
    val data: List<Data>,
    @SerialName("success")
    val success: Boolean
)