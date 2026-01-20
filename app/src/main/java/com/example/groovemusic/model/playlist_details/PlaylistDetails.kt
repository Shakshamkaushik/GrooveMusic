package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDetails(
    @SerialName("data")
    val playlistDetailResult: PlaylistDetailResult? = PlaylistDetailResult(),
    @SerialName("success")
    val success: Boolean? = false
)