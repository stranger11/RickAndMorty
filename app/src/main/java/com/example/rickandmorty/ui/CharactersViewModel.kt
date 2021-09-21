package com.example.rickandmorty.ui

import androidx.lifecycle.*
import com.example.rickandmorty.data.CharacterService
import com.example.rickandmorty.domain.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel : ViewModel() {

    //private val okStatusCodes = 200..299
    private var _characters : MutableLiveData<List<Characters>> =
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
            val response = CharacterService
                .getCharacterService().
                getNextCharacters(page.toString()).results
            withContext(Dispatchers.Main) {
                _characters.value = response.map { Characters.Character(it) }
                addButtonLoad()
            }
        }
    }
}