package com.example.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.rickandmorty.App
import com.example.rickandmorty.domain.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CharactersViewModel : ViewModel() {

    private var _characters: MutableLiveData<List<Characters>> =
        MutableLiveData<List<Characters>>()
    var characters: LiveData<List<Characters>> = _characters

    init {
        getDataNextPage(1)
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

    fun getDataNextPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = App.apiService.getNextCharacters(page.toString()).results
                val charactersList = response.map { Characters.Character(it) }
                withContext(Dispatchers.Main) {
                    _characters.value = charactersList
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