package com.example.rickandmorty.domain

import com.example.rickandmorty.data.network.RickAndMortyService

interface RickAndMortyRepository {

    suspend fun getCharactersPage(page : Int) : List<Characters.Character>
}