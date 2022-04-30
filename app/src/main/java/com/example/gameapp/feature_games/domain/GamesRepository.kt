package com.example.gameapp.feature_games.domain

import androidx.paging.PagingData
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    suspend fun getGames(query: GamesQuery): Flow<PagingData<Game>>
}