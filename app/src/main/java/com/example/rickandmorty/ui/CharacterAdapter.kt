package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.data.Character

class CharacterAdapter(private var onClick: (List<String>) -> Unit) : ListAdapter<Character.Result,
        CharacterViewHolder>(CharacterDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.characters_item_layout,
            parent,
            false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var gender: TextView = view.findViewById(R.id.gender)
    private var type: TextView = view.findViewById(R.id.type)
    private var characterImage: ImageView = view.findViewById(R.id.characterImage)

    fun bind(item: Character.Result, onClick: (List<String>) -> Unit) {
        gender.text = item.gender
        type.text = item.type
        Glide.with(characterImage.context)
            .load(item.image)
            .into(characterImage)

        itemView.setOnClickListener {
            onClick(item.episode)
        }
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<Character.Result>() {
    override fun areItemsTheSame(oldItem: Character.Result, newItem: Character.Result)
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Character.Result, newItem: Character.Result)
            : Boolean = oldItem == newItem
}
