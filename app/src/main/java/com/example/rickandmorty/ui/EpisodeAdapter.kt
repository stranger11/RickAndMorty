package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.Episode

class EpisodeAdapter(private val episodes: List<Episode>) :  RecyclerView.Adapter<EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.episodes_item_layout,
            parent,
            false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val data = episodes[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }
}

class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var episode: TextView = view.findViewById(R.id.episode)

    fun bind(item: Episode) {
        episode.text = item.name
    }
}
