package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsX(
    @SerialName("all")
    val all: List<All>?,
    @SerialName("featured")
    val featured: List<String?>?,
    @SerialName("primary")
    val primary: List<Primary>?
)