package com.example.gameapp.feature_games.presentation.games

import androidx.paging.PagingData
import androidx.paging.filter
import app.cash.turbine.test
import com.example.gameapp.core.util.TestDispatchers
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.data.repository.GamesRepositoryImpl
import com.example.gameapp.feature_games.domain.GenreDataStore
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import com.example.gameapp.feature_games.domain.model.mapper.GameMapper
import com.example.gameapp.feature_games.domain.use_cases.GetGamesUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class GamesViewModelTests {

    private val dispatcherProvider = TestDispatchers()

    @Test
    fun `init, should set genre name`() = runTest {
        val dataStore = mock(GenreDataStore::class.java)

        val useCase = mock(GetGamesUseCase::class.java)

        `when`(dataStore.getGenreName()).thenReturn(flowOf("genreName"))

        val viewModel = GamesViewModel(dataStore, dispatcherProvider, useCase)

        assertThat(viewModel.gamesQueryStateFlow.value.genreName).isEqualTo("genreName")
    }

    @Test
    fun `getGames, genreName is empty string, should send GenreNotSetEvent`() = runTest {
        val dataStore = mock(GenreDataStore::class.java)

        `when`(dataStore.getGenreName()).thenReturn(flowOf(""))

        val useCase = mock(GetGamesUseCase::class.java)

        val viewModel = GamesViewModel(dataStore, dispatcherProvider, useCase)

        viewModel.gamesEvents.test {
            val event = awaitItem()
            assertThat(event).isInstanceOf(GamesViewModel.GamesEvents.GenreNameNotSet::class.java)
        }
    }

    @Test
    fun `onQueryChanged, should set query`() = runTest {
        val dataStore = mock(GenreDataStore::class.java)

        val useCase = mock(GetGamesUseCase::class.java)

        `when`(dataStore.getGenreName()).thenReturn(flowOf("indie"))

        val viewModel = GamesViewModel(dataStore, dispatcherProvider, useCase)

        viewModel.onSearchQueryChanged("search")

        assertThat(viewModel.gamesQueryStateFlow.value.searchQuery).isEqualTo("search")
    }
}