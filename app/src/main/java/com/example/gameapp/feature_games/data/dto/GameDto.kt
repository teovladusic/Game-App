package com.example.gameapp.feature_games.data.dto

import com.google.gson.annotations.SerializedName

data class GameDto(
    val id: Int,
    val name: String,
    @SerializedName("background_image") val backgroundImage: String?,
    val rating: Float,
    @SerializedName("ratings_count") val ratingsCount: Int,
    val genres: List<GenreNamesDto>,
    @SerializedName("short_screenshots") val shortScreenshots: List<ScreenshotDto>,
    @SerializedName("suggestions_count") val suggestionsCount: Int
)