package com.example.groovemusic.model.singlesong


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    @SerialName("all")
    val all: List<All>,
//    @SerialName("featured")
//    val featured: List<Any?>,
    @SerialName("primary")
    val primary: List<Primary>
)