package com.example.rickandmorty.data

import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.RickAndMortyRepository

class RickAndMortyRepositoryImpl : RickAndMortyRepository {

    override suspend fun getCharactersPage(page : Int): List<Characters.Character> {
        val response = ServiceProvider
            .getCharacterService()
            .getNextCharacters(page.toString()).results
        return response.map { Characters.Character(it) }
    }
}