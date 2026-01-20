package com.example.groovemusic.model.album


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Image(
    @SerialName("quality")
    val quality: String,
    @SerialName("url")
    val url: String
) : Parcelable