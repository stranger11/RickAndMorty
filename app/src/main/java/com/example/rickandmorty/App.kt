package com.example.rickandmorty

import android.app.Application
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        repository = RickAndMortyRepositoryImpl()
    }

    companion object {
        lateinit var repository: RickAndMortyRepositoryImpl
        private set
    }
}

