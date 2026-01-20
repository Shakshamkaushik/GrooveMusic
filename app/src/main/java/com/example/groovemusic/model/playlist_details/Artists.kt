package com.example.groovemusic.model.playlist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    @SerialName("all")
    val all: List<All>? = listOf(),
//    @SerialName("featured")
//    val featured: List<Any?>? = listOf(),
    @SerialName("primary")
    val primary: List<Primary>? = listOf()
)