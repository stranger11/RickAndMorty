package com.example.rickandmorty.domain

data class CharacterStorage (
    val charactersList: List<CharacterData>
) {
    data class CharacterData(
        val episode: List<String>,
        val gender: String,
        val image: String,
        val type: String
    )
}
