package com.example.gameapp.feature_games.domain.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gameapp.feature_games.data.repository.GamesPagingSource
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val repository: GamesRepository
) {

    suspend operator fun invoke(gamesQuery: GamesQuery) : Flow<PagingData<Game>> {
        return repository.getGames(gamesQuery)
    }
}