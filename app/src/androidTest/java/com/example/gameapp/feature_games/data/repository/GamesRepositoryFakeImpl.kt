package com.example.gameapp.feature_games.data.repository

import androidx.paging.PagingData
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.dto.GenreNamesDto
import com.example.gameapp.feature_games.data.dto.ScreenshotDto
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GamesRepositoryFakeImpl : GamesRepository {

    override suspend fun getGames(query: GamesQuery): Flow<PagingData<Game>> {
        val games = listOf(
            Game(
                1,
                "game1",
                "backgroundImage",
                5.0f,
                20,
                listOf("indie"),
                listOf("image"),
                20
            ),
            Game(
                2,
                "game2",
                "backgroundImage",
                5.0f,
                20,
                listOf("indie"),
                listOf("image"),
                20
            )
        )

        return flowOf(PagingData.from(games))
    }
}