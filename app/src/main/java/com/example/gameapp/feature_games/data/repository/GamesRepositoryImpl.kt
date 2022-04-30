package com.example.gameapp.feature_games.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gameapp.core.util.Constants
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val api: GamesApiService,
    private val mapper: EntityMapper<GameDto, Game>
) : GamesRepository {

    override suspend fun getGames(query: GamesQuery): Flow<PagingData<Game>> =
        withContext(dispatcherProvider.io) {
            return@withContext Pager(PagingConfig(20)) {
                GamesPagingSource(query, api, mapper)
            }.flow
        }
}
