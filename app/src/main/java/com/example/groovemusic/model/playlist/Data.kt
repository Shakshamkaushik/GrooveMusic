package com.example.groovemusic.model.playlist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("results")
    val playlistResult: List<PlaylistResult>,
    @SerialName("start")
    val start: Int,
    @SerialName("total")
    val total: Int
)