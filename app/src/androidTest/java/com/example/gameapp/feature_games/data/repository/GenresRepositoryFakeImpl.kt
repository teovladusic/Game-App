package com.example.gameapp.feature_games.data.repository

import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult
import kotlinx.coroutines.delay

class GenresRepositoryFakeImpl : GenresRepository {

    companion object {
        var shouldReturnError = false
        var delayTime = 0L
    }

    override suspend fun getGenres(): RepositoryResult<List<Genre>> {
        delay(delayTime)
        val dataToReturn: RepositoryResult<List<Genre>> = if (shouldReturnError) {
            RepositoryResult(false, null, "error message")
        } else {
            val data = listOf(
                Genre(1, "genre1", "slug1", 2, "imageBackground1"),
                Genre(2, "genre2", "slug2", 1, "imageBackground2")
            )
            RepositoryResult(true, data, null)
        }

        setDefaults()

        return dataToReturn
    }

    private fun setDefaults() {
        shouldReturnError = false
        delayTime = 0L
    }
}