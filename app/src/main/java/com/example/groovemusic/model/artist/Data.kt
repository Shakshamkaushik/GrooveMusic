package com.example.groovemusic.model.artist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("results")
    val artistResult: List<ArtistResult>,
    @SerialName("start")
    val start: Int,
    @SerialName("total")
    val total: Int
)