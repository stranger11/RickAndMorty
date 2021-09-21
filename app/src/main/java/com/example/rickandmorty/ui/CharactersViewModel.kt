package com.example.rickandmorty.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.CharacterNW
import com.example.rickandmorty.data.RickAndMortyService
import com.example.rickandmorty.domain.Characters
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://rickandmortyapi.com/api/"

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val okStatusCodes = 200..299
    //var page = 0
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
        getCharacterService()
            .getNextCharacters(page.toString())
            .enqueue(object : Callback<CharacterNW> {
                override fun onResponse(
                    call: Call<CharacterNW>,
                    response: Response<CharacterNW>
                ) {
                    if (response.code() in okStatusCodes) {
                        val charactersList = response.body()!!.results
                        _characters.value = charactersList.map { Characters.Character(it) }
                        addButtonLoad()
                    }
                }
                override fun onFailure(call: Call<CharacterNW>, t: Throwable) {
                    addButtonError()
                    Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getCharacterService(): RickAndMortyService {
        val retrofit = Retrofit
            .Builder()
            .client(okHttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RickAndMortyService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}