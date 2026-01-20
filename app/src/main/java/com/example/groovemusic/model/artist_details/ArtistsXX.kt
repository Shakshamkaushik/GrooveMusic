package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsXX(
    @SerialName("all")
    val all: List<AllXX>?,
    @SerialName("featured")
    val featured: List<String?>?,
    @SerialName("primary")
    val primary: List<PrimaryXX>?
)