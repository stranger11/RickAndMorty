package com.example.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.rickandmorty.domain.Characters
import com.example.rickandmorty.domain.RickAndMortyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CharactersViewModel(repository: RickAndMortyRepository) : ViewModel() {

    private var _characters: MutableLiveData<List<Characters>> =
        MutableLiveData<List<Characters>>()
    var characters: LiveData<List<Characters>> = _characters
    private val repository = repository
    private var page = 1

    init {
        getDataNextPage()
    }

    private fun addButtonLoad() {
        val currentListCharacters = _characters.value.orEmpty().toMutableList()
        currentListCharacters.add(Characters.ButtonLoad)
        _characters.value = currentListCharacters
    }

    private fun addButtonError() {
        val currentListCharacters = _characters.value.orEmpty().toMutableList()
        if (currentListCharacters.isEmpty()) {
            currentListCharacters.add(Characters.Error)
            _characters.value = currentListCharacters
        } else {
            currentListCharacters.removeAt(currentListCharacters.lastIndex)
            currentListCharacters.add(Characters.Error)
            _characters.value = currentListCharacters
        }
    }

    fun getDataNextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _characters.value = repository.getCharactersPage(page)
                    page++
                    addButtonLoad()
                }
            } catch (e : Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("HTTP", e.localizedMessage!!)
                    addButtonError()
                }
            }
        }
    }
}