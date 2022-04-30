package com.example.gameapp.feature_games.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val rating: Float,
    val ratingsCount: Int,
    val genres: List<String>,
    val shortScreenshots: List<String>,
    val suggestionsCount: Int
) : Parcelable