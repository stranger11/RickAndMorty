package com.example.rickandmorty.data

import com.example.rickandmorty.data.network.RickAndMortyService
import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.Episode
import com.example.rickandmorty.domain.RickAndMortyRepository

class RickAndMortyRepositoryImpl(private val service: RickAndMortyService) : RickAndMortyRepository {

    override suspend fun getCharactersPage(page: Int): List<Characters.Character>
        = service.getNextCharacters(page.toString()).results.toDomain()

    override suspend fun getEpisode(list: List<String>): List<Episode> {
        val numbers = list.map { link ->
                link.filter { it.isDigit() }
            }.toString()
        return service.getEpisode(numbers).toDomain()
    }
}












