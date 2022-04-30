package com.example.gameapp.feature_games.domain.model

data class GamesResponse(
    val next: String?,
    val previous: String?,
    val results: List<Game>
)