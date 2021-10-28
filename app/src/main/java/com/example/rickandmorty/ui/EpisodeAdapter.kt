package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.domain.Episode

class EpisodeAdapter : ListAdapter<Episode,
        EpisodeViewHolder>(EpisodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.episodes_item_layout,
            parent,
            false
        )
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var episodeName: TextView = view.findViewById(R.id.nameEpisode)
    private var episodeDate: TextView = view.findViewById(R.id.dateEpisode)

    fun bind(item: Episode) {
        episodeName.text = item.name
        episodeDate.text = item.airDate
    }
}

object EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode)
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode)
            : Boolean = oldItem == newItem
}
