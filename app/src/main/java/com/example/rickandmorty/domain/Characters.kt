package com.example.rickandmorty.domain

sealed class Characters {

    data class Character (
        val item: CharacterStorage.CharacterData
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}