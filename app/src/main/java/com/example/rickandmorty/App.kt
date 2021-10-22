package com.example.rickandmorty

import android.app.Application
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl
import com.example.rickandmorty.data.network.RickAndMortyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://rickandmortyapi.com/api/"

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        repositoryImpl = RickAndMortyRepositoryImpl()
    }

    companion object {
        lateinit var repositoryImpl: RickAndMortyRepositoryImpl
        private set
    }
}

