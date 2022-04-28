package com.example.gameapp.feature_games.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.Resource
import com.example.gameapp.core.util.TestDispatchers
import com.example.gameapp.feature_games.domain.GenreDataStore
import com.example.gameapp.feature_games.domain.GenresRepository
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.model.RepositoryResult
import com.example.gameapp.feature_games.domain.use_cases.GetGenresUseCase
import com.example.gameapp.feature_games.presentation.games_genre_selection.GamesGenreSelectionViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GamesGenreSelectionViewModelTests {

    @Test
    fun onNextPage_shouldSetCurrentPageToOne() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)
        `when`(repository.getGenres()).thenReturn(RepositoryResult(false, null, "error"))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        assertThat(viewModel.currentPageFlow.value).isEqualTo(0)

        viewModel.nextPage()

        assertThat(viewModel.currentPageFlow.value).isEqualTo(1)
    }

    @Test
    fun onPreviousPage_shouldSetCurrentPageToZero() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)
        `when`(repository.getGenres()).thenReturn(RepositoryResult(false, null, "error"))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        assertThat(viewModel.currentPageFlow.value).isEqualTo(0)

        viewModel.nextPage()

        viewModel.previousPage()

        assertThat(viewModel.currentPageFlow.value).isEqualTo(0)
    }

    @Test
    fun setCurrentPage_shouldSetCorrectValue() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)
        `when`(repository.getGenres()).thenReturn(RepositoryResult(false, null, "error"))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        viewModel.setCurrentPage(12)

        assertThat(viewModel.currentPageFlow.value).isEqualTo(12)
    }

    @Test
    fun setSelectedGenrePosition_shouldSetCorrectValue() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)
        `when`(repository.getGenres()).thenReturn(RepositoryResult(false, null, "error"))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        assertThat(viewModel.selectedGenrePosition.value).isEqualTo(-1)

        viewModel.setSelectedGenrePosition(1)

        assertThat(viewModel.selectedGenrePosition.value).isEqualTo(1)
    }

    @Test
    fun `init, should emit getGenresStatus`() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)
        `when`(repository.getGenres()).thenReturn(RepositoryResult(false, null, "error"))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        viewModel.getGenresStatus.test {
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(Resource.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onGenreConfirmed_shouldSendChannelEvent() = runTest {
        val savedStateHandle = SavedStateHandle()
        val testDispatchers = TestDispatchers() as DispatcherProvider
        val repository = mock(GenresRepository::class.java)
        val dataStore = mock(GenreDataStore::class.java)

        val data = listOf(Genre(1, "name", "slug", 1, "imageBackground"))

        `when`(repository.getGenres()).thenReturn(RepositoryResult(true, data, null))
        val viewModel = GamesGenreSelectionViewModel(
            savedStateHandle,
            testDispatchers,
            GetGenresUseCase(repository),
            dataStore
        )

        viewModel.setSelectedGenrePosition(0)
        viewModel.onGenreConfirmed()

        viewModel.gamesGenreSelectionEvents.test {
            val event = awaitItem()
            assertThat(event).isInstanceOf(GamesGenreSelectionViewModel.GamesGenreSelectionEvents.GenreConfirmed::class.java)
        }
    }
}