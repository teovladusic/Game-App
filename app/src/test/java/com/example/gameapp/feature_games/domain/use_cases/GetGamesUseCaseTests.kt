package com.example.gameapp.feature_games.domain.use_cases

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.gameapp.feature_games.domain.GamesRepository
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetGamesUseCaseTests {

    @Test
    fun `invoke, should return flow of paging data`() = runTest {
        val gamesQuery = GamesQuery("indie", "", 1)

        val repository = mock(GamesRepository::class.java)

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

        val repositoryResult = flowOf(PagingData.from(games))

        `when`(repository.getGames(gamesQuery)).thenReturn(repositoryResult)

        val useCase = GetGamesUseCase(repository)

        useCase(gamesQuery).test {
            val data = awaitItem()

            assertThat(data).isInstanceOf(PagingData::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}