package com.example.rickandmorty.domain

interface RickAndMortyRepository {

    suspend fun getCharactersPage(page : Int) : List<Characters.Character>

    suspend fun getEpisode(list: List<String>) : List<Episode>
}