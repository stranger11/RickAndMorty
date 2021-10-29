package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.EpisodesItemLayoutBinding
import com.example.rickandmorty.domain.Episode

class EpisodeAdapter : ListAdapter<Episode,
        EpisodeViewHolder>(EpisodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = EpisodesItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context))
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class EpisodeViewHolder(private val binding: EpisodesItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Episode) {
        binding.nameEpisode.text = item.name
        binding.dateEpisode.text = item.airDate
    }
}

object EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode)
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode)
            : Boolean = oldItem == newItem
}
