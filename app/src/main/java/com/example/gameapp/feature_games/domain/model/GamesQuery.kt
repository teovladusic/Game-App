package com.example.gameapp.feature_games.domain.model

data class GamesQuery(
    var genreName: String,
    var searchQuery: String?,
    var page: Int = 1
)