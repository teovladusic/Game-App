package com.example.gameapp.feature_games.presentation.games_genre_selection.select_games_genre

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.gameapp.R
import com.example.gameapp.databinding.GenreListItemBinding
import com.example.gameapp.feature_games.domain.model.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresAdapter(
    private val genres: List<Genre>
) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        context = parent.context
        val binding =
            GenreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = genres[position]
        holder.bind(genre)
    }

    override fun getItemCount(): Int = genres.size

    inner class GenresViewHolder(
        private val binding: GenreListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(genre: Genre) {
            binding.tvGenreName.text = genre.name
            binding.tvGenreGamesCount.text = "Games: ${genre.gamesCount}"

            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(context).load(genre.imageBackground)
                    .into(binding.imgViewGenreBackground)
            }
        }
    }
}