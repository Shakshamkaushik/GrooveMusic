package com.example.groovemusic.model.singlesong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class All(
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: List<ImageXX>,
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)