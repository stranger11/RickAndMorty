package com.example.rickandmorty.data

import com.example.rickandmorty.data.network.CharacterNW
import com.example.rickandmorty.data.network.EpisodeNW
import com.example.rickandmorty.ui.model.CharacterStorage
import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.Episode

@JvmName("toDomainEpisodeNW")
internal fun List<EpisodeNW>.toDomain(): List<Episode> =
        map {
            it.toDomain()
        }

internal fun EpisodeNW.toDomain() =
        Episode(airDate = this.airDate, name = this.name)

internal fun List<CharacterNW.Result>.toDomain(): List<Characters.Character> =
    map {
        it.toDomain()
    }

internal fun CharacterNW.Result.toDomain() =
    Characters.Character(
        CharacterStorage.CharacterData(
            episode = this.episode,
            gender = this.gender,
            image = this.image,
            type = this.type
        )
    )



