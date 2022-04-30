package com.example.gameapp.feature_games.presentation.games

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameapp.databinding.GameListItemBinding
import com.example.gameapp.feature_games.domain.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesAdapter(
    private val gameClickListener: OnGameClickListener
) : PagingDataAdapter<Game, GamesAdapter.GamesViewHolder>(DiffCallback()) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        context = parent.context
        val binding =
            GameListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = getItem(position) ?: return
        holder.bind(game)
    }

    inner class GamesViewHolder(
        private val binding: GameListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }

                val game = getItem(position) ?: return@setOnClickListener

                gameClickListener.onGameClick(game)
            }
        }

        fun bind(game: Game) {
            binding.tvGameTitle.text = game.name

            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(context)
                    .load(game.backgroundImage)
                    .into(binding.imgViewGameImage)
            }


            binding.tvGameGenres.text = game.genres.take(2)
                .toString()
                .replace("[", "")
                .replace("]", "")

            binding.tvStarsCount.text = game.rating.toString().take(3)
        }
    }

    interface OnGameClickListener {
        fun onGameClick(game: Game)
    }

    class DiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Game, newItem: Game) = oldItem == newItem
    }
}