package com.example.rickandmorty.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET ("episode/{episode}")
    suspend fun getEpisode(@Path("episode") episode:String) : List<EpisodeNW>

    @GET("character")
    suspend fun getNextCharacters(@Query("page") page: String) : CharacterNW
}