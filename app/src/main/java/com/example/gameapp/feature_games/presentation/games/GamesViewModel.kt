package com.example.gameapp.feature_games.presentation.games

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gameapp.core.util.DispatcherProvider
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.core.util.Resource
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.data.remote.GamesApiService
import com.example.gameapp.feature_games.data.repository.GamesPagingSource
import com.example.gameapp.feature_games.domain.GenreDataStore
import com.example.gameapp.feature_games.domain.model.Game
import com.example.gameapp.feature_games.domain.model.GamesQuery
import com.example.gameapp.feature_games.domain.model.GamesResponse
import com.example.gameapp.feature_games.domain.use_cases.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GamesViewModel @Inject constructor(
    private val dataStore: GenreDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    val gamesQueryStateFlow = MutableStateFlow(GamesQuery("", null))

    private val gamesEventsChannel = Channel<GamesEvents>()
    val gamesEvents = gamesEventsChannel.receiveAsFlow()

    val gamesFlow = gamesQueryStateFlow.flatMapLatest { gamesQuery ->
        withContext(dispatcherProvider.io) {
            getGames(gamesQuery)
        }
    }.shareIn(viewModelScope + dispatcherProvider.default, SharingStarted.Eagerly, 1)

    private suspend fun getGames(gamesQuery: GamesQuery): Flow<PagingData<Game>> {
        return getGamesUseCase(gamesQuery).cachedIn(viewModelScope + dispatcherProvider.default)
    }

    fun refreshGames() = viewModelScope.launch(dispatcherProvider.default) {
        val currentQuery = gamesQueryStateFlow.value
        val newQuery = currentQuery.copy(searchQuery = "x")
        gamesQueryStateFlow.emit(newQuery)
        delay(10)
        gamesQueryStateFlow.emit(currentQuery)
    }

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.getGenreName().collectLatest {
                if (it == "") {
                    sendGamesEvent(GamesEvents.GenreNameNotSet)
                    return@collectLatest
                }

                val newQuery = gamesQueryStateFlow.value.copy(genreName = it)
                gamesQueryStateFlow.emit(newQuery)
            }
        }
    }

    fun onSearchQueryChanged(searchQuery: String) =
        viewModelScope.launch(dispatcherProvider.default) {
            val search = if (searchQuery == "") null else searchQuery
            val query = gamesQueryStateFlow.value.copy(searchQuery = search)
            gamesQueryStateFlow.emit(query)
        }

    private fun sendGamesEvent(event: GamesEvents) =
        viewModelScope.launch(dispatcherProvider.default) {
            gamesEventsChannel.send(event)
        }

    sealed class GamesEvents {
        object GenreNameNotSet : GamesEvents()
    }
}