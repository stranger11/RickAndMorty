package com.example.rickandmorty.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.Episode
import com.example.rickandmorty.retrofit.RickAndMortyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://rickandmortyapi.com/api/"

class SharedViewModel : ViewModel() {

    private val okStatusCodes = 200..299
    var linksEpisodes: List<String>? = null
    var idOfEpisodes: String? = null


    fun getEpisode() : List<Episode> {
        val listResult = mutableListOf<Episode>()
        linksEpisodes?.forEach {
            Log.d("SPISOK", it)
            val a = it.substring(40)
            getEpisodeService()
                .getEpisode(a)
                .enqueue(object : Callback<Episode> {
                    override fun onResponse(
                        call: Call<Episode>,
                        response: Response<Episode>
                    ) {
                        if (response.code() in okStatusCodes) {
                            val responseEpisodes = response.body()!!
                            listResult.add(responseEpisodes)
                        }
                    }

                    override fun onFailure(call: Call<Episode>, t: Throwable) {
                        //Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
        return listResult
    }

    private fun getEpisodeService(): RickAndMortyService {
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