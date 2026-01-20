package com.example.groovemusic.model.album


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("results")
    val albumResult: List<AlbumResult>,
    @SerialName("start")
    val start: Int,
    @SerialName("total")
    val total: Int
)