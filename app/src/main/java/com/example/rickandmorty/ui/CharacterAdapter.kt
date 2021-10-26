package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharactersItemLayoutBinding
import com.example.rickandmorty.domain.Characters

import java.lang.IllegalArgumentException

class CharacterAdapter(
    private var onClick: (List<String>) -> Unit,
    private var loadButton: () -> Unit,
    private var errorButton: () -> Unit
) : ListAdapter<Characters,
        RecyclerView.ViewHolder>(CharacterDiffCallback) {

    companion object {
        const val VIEW_CHARACTER = 1
        const val VIEW_LOAD_BUTTON = 2
        const val VIEW_ERROR_BUTTON = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_CHARACTER -> {
                val binding = CharactersItemLayoutBinding
                    .inflate(LayoutInflater.from(parent.context))
                return CharacterViewHolder(binding)
            }

            VIEW_LOAD_BUTTON -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.load_button_layout,
                    parent,
                    false
                )
                return ButtonViewHolder(view)
            }

            VIEW_ERROR_BUTTON -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.error_item_layout,
                    parent,
                    false
                )
                return ErrorViewHolder(view)
            }

            else -> throw IllegalArgumentException("failed")
        }
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is Characters.Character -> VIEW_CHARACTER
            is Characters.ButtonLoad -> VIEW_LOAD_BUTTON
            is Characters.Error -> VIEW_ERROR_BUTTON
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterViewHolder -> holder.bind(
                getItem(position) as Characters.Character,
                onClick
            )
            is ButtonViewHolder -> holder.bind(loadButton)
            is ErrorViewHolder -> holder.bind(errorButton)
        }
    }
}

class CharacterViewHolder(private val binding: CharactersItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Characters.Character, onClick: (List<String>) -> Unit) {
        binding.gender.text = item.item.gender
        binding.type.text = item.item.gender
        Glide.with(binding.characterImage.context)
            .load(item.item.image)
            .into(binding.characterImage)

        itemView.setOnClickListener {
            onClick(item.item.episode)
        }
    }
}

class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var buttonLoad: Button = view.findViewById(R.id.buttonLoad)

    fun bind(callback: () -> Unit) {
        buttonLoad.setOnClickListener {
            callback()
        }
    }
}

class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var buttonError: Button = view.findViewById(R.id.buttonError)

    fun bind(callback: () -> Unit) {
        buttonError.setOnClickListener {
            callback.invoke()
        }
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<Characters>() {
    override fun areItemsTheSame(oldItem: Characters, newItem: Characters)
            : Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Characters, newItem: Characters)
            : Boolean = oldItem == newItem
}
