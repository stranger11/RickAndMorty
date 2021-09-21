package com.example.rickandmorty.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    fun getCharacters() : Call<CharacterNW>

    @GET ("episode/{episode}")
    fun getEpisode(@Path("episode") episode:String) : Call <List<EpisodeNW>>

    @GET("character")
    fun getNextCharacters(@Query("page") page: String) : Call <CharacterNW>
}