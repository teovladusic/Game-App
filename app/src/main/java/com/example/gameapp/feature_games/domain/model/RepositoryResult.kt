package com.example.gameapp.feature_games.domain.model

data class RepositoryResult<T>(
    val isSuccess: Boolean,
    val data: T?,
    val errorMessage: String?
)