package com.example.gameapp.feature_games.data.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    val id: Int,

    val name: String,

    val slug: String,

    @SerializedName("games_count")
    val gamesCount: Int,

    @SerializedName("image_background")
    val imageBackground: String
)