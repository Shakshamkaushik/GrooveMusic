package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Song(
    @SerialName("data")
    val `data`: Data,
    @SerialName("success")
    val success: Boolean
): Parcelable