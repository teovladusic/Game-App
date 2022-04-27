package com.example.gameapp.feature_games.presentation.games_genre_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.core.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesGenreSelectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

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

}