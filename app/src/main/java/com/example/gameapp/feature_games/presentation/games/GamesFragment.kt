package com.example.gameapp.feature_games.presentation.games

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameapp.R
import com.example.gameapp.core.util.EntityMapper
import com.example.gameapp.core.util.safeNavigate
import com.example.gameapp.databinding.FragmentGamesBinding
import com.example.gameapp.feature_games.data.dto.GameDto
import com.example.gameapp.feature_games.domain.model.Game
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GamesFragment : Fragment(R.layout.fragment_games), GamesAdapter.OnGameClickListener {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    private lateinit var gamesAdapter: GamesAdapter

    @Inject
    lateinit var mapper: EntityMapper<GameDto, Game>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamesBinding.bind(view)

        setSearchViewColors()

        gamesAdapter = GamesAdapter(this)
        binding.recViewGames.adapter = gamesAdapter
        binding.recViewGames.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gamesFlow.collect {
                    onGetGamesSuccess(it)
                }
            }
        }

        binding.imgViewSettings.setOnClickListener {
            selectGenre()
        }

        binding.btnRetryLoadingGames.setOnClickListener {
            viewModel.refreshGames()
        }

        binding.searchViewGames.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText ?: "")
                return true
            }
        })

        gamesAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) onGetGamesLoading()

            if (it.refresh is LoadState.NotLoading) onGamesLoaded()

            if (it.refresh is LoadState.Error) onGetGamesError()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gamesEvents.collectLatest { event ->
                    when (event) {
                        GamesViewModel.GamesEvents.GenreNameNotSet -> {
                            goToGenreSelection()
                        }
                    }
                }
            }
        }
    }

    private fun setSearchViewColors() {
        val searchIcon: ImageView =
            binding.searchViewGames.findViewById(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray))

        val closeButton: ImageView =
            binding.searchViewGames.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray))

        val searchViewText: EditText =
            binding.searchViewGames.findViewById(androidx.appcompat.R.id.search_src_text)
        searchViewText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
    }

    override fun onGameClick(game: Game) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game)
        findNavController().safeNavigate(action)
    }

    private fun selectGenre() {
        val action = GamesFragmentDirections.actionGamesFragmentToGamesGenreSelectionFragment()
        findNavController().safeNavigate(action)
    }

    private fun onGamesLoaded() {
        binding.lottieAnimationLoading.visibility = View.GONE
    }

    private fun onGetGamesError() {
        binding.lottieAnimationLoading.visibility = View.GONE

        binding.btnRetryLoadingGames.visibility = View.VISIBLE
        binding.imgViewLoadingError.visibility = View.VISIBLE
    }

    private fun onGetGamesLoading() {
        binding.lottieAnimationLoading.visibility = View.VISIBLE
        binding.btnRetryLoadingGames.visibility = View.GONE
        binding.imgViewLoadingError.visibility = View.GONE
    }

    private fun onGetGamesSuccess(data: PagingData<Game>) {
        setUpGamesList(data)
        binding.lottieAnimationLoading.visibility = View.GONE
        binding.btnRetryLoadingGames.visibility = View.GONE
        binding.imgViewLoadingError.visibility = View.GONE
    }

    private fun goToGenreSelection() {
        val action = GamesFragmentDirections.actionGamesFragmentToGamesGenreSelectionFragment()
        findNavController().safeNavigate(action)
    }

    private fun setUpGamesList(games: PagingData<Game>) = viewLifecycleOwner.lifecycleScope.launch {
        gamesAdapter.submitData(games)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}