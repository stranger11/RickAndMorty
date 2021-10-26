package com.example.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.ServiceProvider
import com.example.rickandmorty.data.network.EpisodeNW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeViewModel : ViewModel() {

    private var _episodes: MutableLiveData<List<EpisodeNW>> =
        MutableLiveData<List<EpisodeNW>>()
    var episodes: LiveData<List<EpisodeNW>> = _episodes

    fun getEpisode(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val numbers = list.map { link ->
                link.filter { it.isDigit() } }.toString()
            withContext(Dispatchers.Main) {
                _episodes.value = ServiceProvider.getCharacterService().getEpisode(numbers)
            }
        }
    }
}