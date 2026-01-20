package com.example.groovemusic.model.artist

import kotlinx.serialization.Serializable

@Serializable
data class ArtistsResponse(
    val success: Boolean,
    val data: Data
)