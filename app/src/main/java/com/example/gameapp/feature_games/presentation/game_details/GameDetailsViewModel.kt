package com.example.gameapp.feature_games.presentation.game_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gameapp.feature_games.domain.model.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val game = savedStateHandle.getLiveData<Game>("game")
}