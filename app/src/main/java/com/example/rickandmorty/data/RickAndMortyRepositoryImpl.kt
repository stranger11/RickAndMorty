package com.example.rickandmorty.data

import com.example.rickandmorty.data.network.RickAndMortyService
import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.RickAndMortyRepository

class RickAndMortyRepositoryImpl(private val service: RickAndMortyService) : RickAndMortyRepository {

    override suspend fun getCharactersPage(page: Int): List<Characters.Character> {
        return service.getNextCharacters(page.toString()).results.map { Characters.Character(it) }
    }
}