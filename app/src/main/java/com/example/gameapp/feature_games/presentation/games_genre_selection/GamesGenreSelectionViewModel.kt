package com.example.gameapp.feature_games.presentation.games_genre_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.Resource
import com.example.gameapp.feature_games.domain.GenreDataStore
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.domain.use_cases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GamesGenreSelectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
    private val getGenresUseCase: GetGenresUseCase,
    private val dataStore: GenreDataStore
) : ViewModel() {

    private val gamesGenreSelectionEventsChannel = Channel<GamesGenreSelectionEvents>()
    val gamesGenreSelectionEvents = gamesGenreSelectionEventsChannel.receiveAsFlow()

    var genreLastPosition = -1

    private var loadedGenres = emptyList<Genre>()

    private val _getGenresStatus = MutableSharedFlow<Resource<List<Genre>>>(1)
    val getGenresStatus = _getGenresStatus.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            getGenresUseCase().collectLatest { status ->
                _getGenresStatus.emit(status)

                if (status::class == Resource.Success::class) {
                    loadedGenres = status.data ?: emptyList()
                }
            }
        }
    }

    private val _currentPageFlow = MutableStateFlow(0)
    val currentPageFlow = _currentPageFlow.asStateFlow()

    fun nextPage() = viewModelScope.launch(dispatcherProvider.default) {
        _currentPageFlow.emit(1)
    }

    fun previousPage() = viewModelScope.launch(dispatcherProvider.default) {
        _currentPageFlow.emit(0)
    }

    fun setCurrentPage(page: Int) = viewModelScope.launch(dispatcherProvider.default) {
        _currentPageFlow.emit(page)
    }

    private val _selectedGenrePosition = MutableStateFlow(-1)
    val selectedGenrePosition = _selectedGenrePosition.asStateFlow()

    fun setSelectedGenrePosition(position: Int) =
        viewModelScope.launch(dispatcherProvider.default) {
            _selectedGenrePosition.emit(position)
        }

    fun onGenreConfirmed() = viewModelScope.launch(dispatcherProvider.default) {
        val selectedGenrePosition = selectedGenrePosition.value
        if (selectedGenrePosition == -1) {
            return@launch
        }

        val genre = loadedGenres[selectedGenrePosition]

        withContext(dispatcherProvider.io) {
            dataStore.setGenreName(genre.name.lowercase())
        }

        sendGamesGenreSelectionEventToChannel(GamesGenreSelectionEvents.GenreConfirmed)
    }

    private fun sendGamesGenreSelectionEventToChannel(event: GamesGenreSelectionEvents) =
        viewModelScope.launch(dispatcherProvider.default) {
            gamesGenreSelectionEventsChannel.send(event)
        }

    sealed class GamesGenreSelectionEvents {
        object GenreConfirmed : GamesGenreSelectionEvents()
    }
}