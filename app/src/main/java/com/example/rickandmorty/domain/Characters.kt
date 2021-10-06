package com.example.rickandmorty.domain

import com.example.rickandmorty.data.CharacterNW

sealed class Characters {

    data class Character (
        val item: CharacterNW.Result
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}