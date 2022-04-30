package com.example.gameapp.feature_games.presentation.game_details

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameapp.R
import com.example.gameapp.core.util.safeNavigate
import com.example.gameapp.databinding.FragmentGameDetailsBinding
import com.example.gameapp.feature_games.domain.model.Game
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameDetailsBinding.bind(view)

        viewModel.game.observe(viewLifecycleOwner) { game ->
            setGameUi(game)
            setScreenshotsList(game.shortScreenshots)
        }

        binding.imgViewBackToGames.setOnClickListener {
            val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToGamesFragment()
            findNavController().safeNavigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGameUi(game: Game) {
        binding.apply {
            tvGameDetailsTitle.text = game.name
            tvGameDetailsGenres.text =
                game.genres.take(2).toString().replace("[", "").replace("]", "")
            tvGameDetailsRating.text = game.rating.toString().take(3)
            tvLikes.text = game.suggestionsCount.toString()
            tvRatingsCount.text = "${game.ratingsCount} ratings"
            tvRating.text = game.rating.toString().take(3)

            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(requireContext())
                    .load(game.backgroundImage)
                    .into(imgViewGameMainImage)

                Glide.with(requireContext())
                    .load(game.backgroundImage)
                    .into(imgViewGameDetailsBackground)
            }

            when (game.rating.toString().first().digitToInt()) {
                1 -> {
                    binding.imgViewStar2.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar3.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar4.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar5.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                }

                2 -> {
                    binding.imgViewStar3.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar4.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar5.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                }

                3 -> {
                    binding.imgViewStar4.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                    binding.imgViewStar5.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                }

                4 -> {
                    binding.imgViewStar5.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray
                        )
                    )
                }
            }
        }
    }

    private fun setScreenshotsList(screenshots: List<String>) {
        val screenshotsAdapter = ScreenshotsAdapter(screenshots)
        binding.recViewGameScreenshots.adapter = screenshotsAdapter
        binding.recViewGameScreenshots.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}