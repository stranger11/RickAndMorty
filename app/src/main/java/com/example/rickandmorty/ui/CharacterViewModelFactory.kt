package com.example.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.data.RickAndMortyRepositoryImpl

class CharacterViewModelFactory(private val repositoryImpl: RickAndMortyRepositoryImpl) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(repositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}