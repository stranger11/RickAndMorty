package com.example.rickandmorty.domain

import com.example.rickandmorty.data.network.CharacterNW

sealed class Characters {

    data class Character (
        val item: CharacterNW.Result
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}