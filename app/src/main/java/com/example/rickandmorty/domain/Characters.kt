package com.example.rickandmorty.domain

sealed class Characters {

    data class Character (
        val character: CharacterStorage.CharacterData
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}