package com.example.gameapp.feature_games.data.dto

data class GameResponseDto(
    val next: String?,
    val previous: String?,
    val results: List<GameDto>
)