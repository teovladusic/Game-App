package com.example.gameapp.feature_games.presentation.games_genre_selection.select_games_genre

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentSelectGamesGenreBinding
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.presentation.games_genre_selection.GamesGenreSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectGamesGenreFragment : Fragment(R.layout.fragment_select_games_genre) {

    private var _binding: FragmentSelectGamesGenreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesGenreSelectionViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectGamesGenreBinding.bind(view)

        binding.tvBackToWelcomeScreen.setOnClickListener {
            viewModel.previousPage()
        }

        val genre1 = Genre(
            1,
            "Action",
            "slug",
            200,
            "https://media.rawg.io/media/resize/640/-/games/d82/d82990b9c67ba0d2d09d4e6fa88885a7.jpg"
        )
        val genre2 = Genre(
            2,
            "Indie",
            "slug",
            200,
            "https://media.rawg.io/media/resize/640/-/games/b6b/b6b20bfc4b34e312dbc8aac53c95a348.jpg"
        )
        val genre3 = Genre(
            3,
            "Adventure",
            "slug",
            200,
            "https://media.rawg.io/media/resize/640/-/games/b7d/b7d3f1715fa8381a4e780173a197a615.jpg"
        )
        val genre4 = Genre(
            4,
            "Rpg",
            "slug",
            200,
            "https://media.rawg.io/media/resize/640/-/games/21c/21cc15d233117c6809ec86870559e105.jpg"
        )

        val genres = listOf(genre1, genre2, genre3, genre4)

        val genresAdapter = GenresAdapter(genres)

        binding.recViewGenres.adapter = genresAdapter
        binding.recViewGenres.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}