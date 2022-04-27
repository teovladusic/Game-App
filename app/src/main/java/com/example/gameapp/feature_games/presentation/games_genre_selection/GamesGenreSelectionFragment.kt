package com.example.gameapp.feature_games.presentation.games_genre_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGamesGenreSelectionBinding
import com.example.gameapp.feature_games.presentation.games_genre_selection.games_genre_selection_welcome.GamesGenreSelectionWelcomeFragment
import com.example.gameapp.feature_games.presentation.games_genre_selection.select_games_genre.SelectGamesGenreFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesGenreSelectionFragment : Fragment(R.layout.fragment_games_genre_selection) {

    private var _binding: FragmentGamesGenreSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesGenreSelectionViewModel by viewModels()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamesGenreSelectionBinding.bind(view)

        viewPager = binding.genreSelectionViewPager

        setViewPager()
        registerPageChangeCallback()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentPageFlow.collectLatest { currentPage ->
                    moveToPage(currentPage)
                }
            }
        }
    }

    private fun registerPageChangeCallback() {
        val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentPage(position)
            }
        }

        binding.genreSelectionViewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun moveToPage(page: Int) {
        binding.genreSelectionViewPager.setCurrentItem(page, true)
    }

    private fun setViewPager() {
        val fragmentsList = listOf(GamesGenreSelectionWelcomeFragment(), SelectGamesGenreFragment())
        val viewPagerAdapter =
            GamesGenreSelectionViewPagerAdapter(fragmentsList, childFragmentManager, lifecycle)
        binding.genreSelectionViewPager.adapter = viewPagerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}