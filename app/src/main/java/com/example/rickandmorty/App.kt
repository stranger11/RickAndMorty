package com.example.rickandmorty

import android.app.Application
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl
import com.example.rickandmorty.data.ServiceProvider
import com.example.rickandmorty.data.network.RickAndMortyService
import com.example.rickandmorty.domain.RickAndMortyRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        service = ServiceProvider.getCharacterService()
        repository = RickAndMortyRepositoryImpl(service)
    }

    companion object {
        lateinit var repository: RickAndMortyRepository
        lateinit var service: RickAndMortyService
            private set
    }
}

