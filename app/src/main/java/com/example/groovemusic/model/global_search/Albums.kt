package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Albums(
    @SerialName("position")
    val position: Int? = null,
    @SerialName("results")
    val results: List<Result>? = null
)