package com.example.gameapp.feature_games.presentation.game_details

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameapp.databinding.ScreenshotItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenshotsAdapter(
    private val screenShots: List<String>
) : RecyclerView.Adapter<ScreenshotsAdapter.ScreenshotsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        context = parent.context
        val binding = ScreenshotItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ScreenshotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) {
        val screenshot = screenShots[position]
        holder.bind(screenshot)
    }

    override fun getItemCount(): Int = screenShots.size

    inner class ScreenshotsViewHolder(private val binding: ScreenshotItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(screenshot: String) {
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(context)
                    .load(screenshot)
                    .into(binding.imgViewScreenshot)
            }
        }
    }
}