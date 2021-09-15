package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.Character
import com.example.rickandmorty.data.Episode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("character")
    fun getCharacters() : Call<Character>

    @GET ("episode/{episode}")
    fun getEpisode(@Path("episode") episode:String) : Call<Episode>
}