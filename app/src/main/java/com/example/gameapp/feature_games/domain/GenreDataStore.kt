package com.example.gameapp.feature_games.domain

import kotlinx.coroutines.flow.Flow

interface GenreDataStore {

    suspend fun setGenreName(name: String)

    suspend fun getGenreName() : Flow<String>
}