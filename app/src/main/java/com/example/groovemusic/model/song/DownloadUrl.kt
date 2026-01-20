package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class DownloadUrl(
    @SerialName("quality")
    val quality: String,
    @SerialName("url")
    val url: String
) : Parcelable