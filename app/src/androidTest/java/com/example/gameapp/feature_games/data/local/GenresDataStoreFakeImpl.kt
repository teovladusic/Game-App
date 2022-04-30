package com.example.gameapp.feature_games.data.local

import com.example.gameapp.feature_games.domain.GenreDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GenresDataStoreFakeImpl : GenreDataStore {

    companion object {
        var genreName = ""
    }

    override suspend fun setGenreName(name: String) {
        genreName = name
    }

    override suspend fun getGenreName(): Flow<String> {
        return flowOf(genreName)
    }
}