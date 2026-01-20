package com.example.groovemusic.model.global_search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Songs(
    @SerialName("position")
    val position: Int? = 0,
    @SerialName("results")
    val results: List<ResultXXX>? = listOf()
)