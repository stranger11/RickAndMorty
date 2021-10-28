package com.example.rickandmorty.domain

sealed class Characters {

    data class Character (
        val item: CharacterInfo.Result
    ) : Characters()

    object ButtonLoad : Characters()

    object Error : Characters()

}