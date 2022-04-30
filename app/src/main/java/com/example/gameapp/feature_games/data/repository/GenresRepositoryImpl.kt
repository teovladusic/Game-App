package com.example.gameapp.feature_games.data.repository

import com.example.gameapp.core.util.Constants
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.core.util.safeApiCall
import com.example.gameapp.feature_games.data.dto.GenreDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: GamesApiService,
    private val genresMapper: EntityMapper<GenreDto, Genre>
) : GenresRepository {

    override suspend fun getGenres(): RepositoryResult<List<Genre>> =
        withContext(dispatcherProvider.io) {
            val response = safeApiCall {
                apiService.getGenres(Constants.API_KEY)
            }

            return@withContext if (response == null || !response.isSuccessful) {
                RepositoryResult(false, null, "An error occurred.")
            } else {
                val domainModels =
                    response.body()?.results?.map { genresMapper.mapFromDto(it) } ?: emptyList()
                RepositoryResult(true, domainModels, null)
            }
        }
}