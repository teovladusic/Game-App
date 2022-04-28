package com.example.gameapp.feature_games.domain

import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult

interface GenresRepository {

    suspend fun getGenres(): RepositoryResult<List<Genre>>
}