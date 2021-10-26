package com.example.rickandmorty

import android.app.Application
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl
import com.example.rickandmorty.domain.RickAndMortyRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        repository = RickAndMortyRepositoryImpl()
    }

    companion object {
        lateinit var repository: RickAndMortyRepository
        private set
    }
}

