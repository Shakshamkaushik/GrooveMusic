package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Artists(
    @SerialName("all")
    val all: List<All>,
//    @SerialName("featured")
//    val featured: List<Any?>,
    @SerialName("primary")
    val primary: List<Primary>
) : Parcelable