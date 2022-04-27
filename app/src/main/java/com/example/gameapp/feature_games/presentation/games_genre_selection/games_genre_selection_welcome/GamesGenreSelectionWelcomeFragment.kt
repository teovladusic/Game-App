package com.example.gameapp.feature_games.presentation.games_genre_selection.games_genre_selection_welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGamesGenreSelectionBinding
import com.example.gameapp.databinding.FragmentGamesGenreSelectionWelcomeBinding
import com.example.gameapp.feature_games.presentation.games_genre_selection.GamesGenreSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesGenreSelectionWelcomeFragment :
    Fragment(R.layout.fragment_games_genre_selection_welcome) {

    private var _binding: FragmentGamesGenreSelectionWelcomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesGenreSelectionViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamesGenreSelectionWelcomeBinding.bind(view)

        binding.btnGetStarted.setOnClickListener {
            viewModel.nextPage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}