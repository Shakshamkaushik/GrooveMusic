package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultX(
    @SerialName("description")
    val description: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("image")
    val image: List<Image>? = listOf(),
    @SerialName("position")
    val position: Int? = 0,
    @SerialName("title")
    val title: String? = "",
    @SerialName("type")
    val type: String? = ""
)