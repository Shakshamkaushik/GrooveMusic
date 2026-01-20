package com.example.groovemusic.model.album


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class All(
    @SerialName("id")
    val id: String,
//    @SerialName("image")
//    val image: List<Any>,
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
) : Parcelable