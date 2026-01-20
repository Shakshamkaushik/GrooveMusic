package com.example.groovemusic.model.artist_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("availableLanguages")
    val availableLanguages: List<String>?,
    @SerialName("bio")
    val bio: List<Bio>?,
    @SerialName("dob")
    val dob: String?,
    @SerialName("dominantLanguage")
    val dominantLanguage: String?,
    @SerialName("dominantType")
    val dominantType: String?,
    @SerialName("fanCount")
    val fanCount: String?,
    @SerialName("fb")
    val fb: String?,
    @SerialName("followerCount")
    val followerCount: Int?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: List<Image>?,
    @SerialName("isRadioPresent")
    val isRadioPresent: Boolean?,
    @SerialName("isVerified")
    val isVerified: Boolean?,
    @SerialName("name")
    val name: String?,
//    @SerialName("similarArtists")
//    val similarArtists: List<String?>?,
    @SerialName("singles")
    val singles: List<Single>?,
    @SerialName("topAlbums")
    val topAlbums: List<TopAlbum>?,
    @SerialName("topSongs")
    val topSongs: List<TopSong>?,
    @SerialName("twitter")
    val twitter: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("wiki")
    val wiki: String?
)