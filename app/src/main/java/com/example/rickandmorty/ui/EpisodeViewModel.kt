package com.example.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.domain.Episode
import com.example.rickandmorty.domain.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {

    private var _episodes: MutableLiveData<List<Episode>> =
        MutableLiveData<List<Episode>>()
    var episodes: LiveData<List<Episode>> = _episodes

    fun getEpisode(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val episodesResponse = repository.getEpisode(list)
            withContext(Dispatchers.Main) {
                _episodes.value = episodesResponse
            }
        }
    }
}