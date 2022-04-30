package com.example.gameapp.feature_games.presentation.games_genre_selection.select_games_genre

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameapp.R
import com.example.gameapp.core.util.Resource
import com.example.gameapp.databinding.FragmentSelectGamesGenreBinding
import com.example.gameapp.feature_games.domain.model.Genre
import com.example.gameapp.feature_games.presentation.games_genre_selection.GamesGenreSelectionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectGamesGenreFragment : Fragment(R.layout.fragment_select_games_genre),
    GenresAdapter.OnGenreClickListener {

    private var _binding: FragmentSelectGamesGenreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesGenreSelectionViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectGamesGenreBinding.bind(view)

        binding.imgViewBackToWelcomeScreen.setOnClickListener {
            viewModel.previousPage()
        }

        binding.fabConfirmGenre.setOnClickListener {
            viewModel.onGenreConfirmed()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getGenresStatus.collectLatest { status ->
                    when (status) {
                        is Resource.Error -> handleGetGenresError(status.message!!)
                        is Resource.Loading -> handleGetGenresLoading()
                        is Resource.Success -> handleGetGenresSuccess(status.data!!)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedGenrePosition.collectLatest { position ->
                    changeFloatingActionBtnBackground(position)
                    if (binding.recViewGenres.adapter == null) return@collectLatest
                    refreshAdapter(position)
                }
            }
        }
    }

    private fun handleGetGenresError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        binding.lottieAnimationLoading.visibility = View.GONE
    }


    private fun handleGetGenresLoading() {
        binding.lottieAnimationLoading.visibility = View.VISIBLE
    }

    private fun handleGetGenresSuccess(genres: List<Genre>) {
        binding.lottieAnimationLoading.visibility = View.GONE
        val genresAdapter = GenresAdapter(genres, this)

        binding.recViewGenres.adapter = genresAdapter
        binding.recViewGenres.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun changeFloatingActionBtnBackground(position: Int) {
        if (position != -1) {
            binding.fabConfirmGenre.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.pink
                )
            )
        } else {
            binding.fabConfirmGenre.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        }
    }

    private fun refreshAdapter(selectedGenrePosition: Int) {
        val adapter = binding.recViewGenres.adapter as GenresAdapter
        adapter.selectedGenrePosition = selectedGenrePosition
        adapter.notifyItemChanged(selectedGenrePosition)
        adapter.notifyItemChanged(viewModel.genreLastPosition)
        viewModel.genreLastPosition = selectedGenrePosition
    }

    override fun onGenreClick(position: Int) {
        viewModel.setSelectedGenrePosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}