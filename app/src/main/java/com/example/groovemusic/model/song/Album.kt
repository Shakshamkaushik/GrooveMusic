package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Album(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
) : Parcelable