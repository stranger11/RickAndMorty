package com.example.rickandmorty.data

import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.RickAndMortyRepository

class RickAndMortyRepositoryImpl : RickAndMortyRepository {

    override suspend fun getCharactersPage(page: Int): List<Characters.Character> {
        return ServiceProvider
            .getCharacterService()
            .getNextCharacters(page.toString()).results.map { Characters.Character(it) }
    }
}