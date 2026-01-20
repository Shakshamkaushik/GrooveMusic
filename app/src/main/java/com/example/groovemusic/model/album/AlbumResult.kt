package com.example.groovemusic.model.album


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class AlbumResult(
    @SerialName("artists")
    val artists: Artists,
    @SerialName("description")
    val description: String,
    @SerialName("explicitContent")
    val explicitContent: Boolean,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: List<Image>,
    @SerialName("language")
    val language: String,
    @SerialName("name")
    val name: String,
    @SerialName("songCount")
    val songCount: Int ? = null,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("year")
    val year: Int
) : Parcelable