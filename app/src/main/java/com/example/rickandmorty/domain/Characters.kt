package com.example.rickandmorty.domain

import com.example.rickandmorty.ui.model.CharacterStorage

sealed class Characters {

    data class Character (
        val character: CharacterStorage.CharacterData
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}