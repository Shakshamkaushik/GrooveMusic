package com.example.groovemusic.model.song


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Data(
    @SerialName("results")
    val results: List<SongResult>,
    @SerialName("start")
    val start: Int,
    @SerialName("total")
    val total: Int
) : Parcelable