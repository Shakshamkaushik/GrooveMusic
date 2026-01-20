package com.example.groovemusic.model.singlesong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)