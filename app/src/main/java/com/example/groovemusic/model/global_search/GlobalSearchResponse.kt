package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalSearchResponse(
    @SerialName("albums")
    val albums: Albums? = Albums(),
    @SerialName("artists")
    val artists: Artists? = Artists(),
    @SerialName("playlists")
    val playlists: Playlists? = Playlists(),
    @SerialName("songs")
    val songs: Songs? = Songs(),
    @SerialName("topQuery")
    val topQuery: TopQuery? = TopQuery()
)