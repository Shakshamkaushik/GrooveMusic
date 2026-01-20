package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalSearch(
    @SerialName("data")
    val globalSearchResponse: GlobalSearchResponse? = GlobalSearchResponse(),
    @SerialName("success")
    val success: Boolean? = false
)