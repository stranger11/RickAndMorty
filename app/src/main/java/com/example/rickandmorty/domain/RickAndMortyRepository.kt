package com.example.rickandmorty.domain

interface RickAndMortyRepository {

    suspend fun getCharactersPage(page : Int) : List<Characters.Character>
}