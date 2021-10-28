package com.example.rickandmorty.domain

data class CharacterInfo (
    val results: List<Result>
) {
    data class Result(
        val episode: List<String>,
        val gender: String,
        val image: String,
        val type: String
    )
}
