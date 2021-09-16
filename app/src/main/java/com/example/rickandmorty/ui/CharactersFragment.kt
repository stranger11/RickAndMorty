package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.data.Character
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.data.RickAndMortyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://rickandmortyapi.com/api/"

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val okStatusCodes = 200..299
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initRecyclerView()
        getCurrentData()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
        initAdapter()
        binding.recyclerViewCharacters.adapter = characterAdapter
    }

    private fun initAdapter() {
        characterAdapter = CharacterAdapter { listLinks ->
            sharedViewModel.linksEpisodes = listLinks
            parentFragmentManager.
            beginTransaction()
                .replace(R.id.fragment_container, EpisodeFragment()).commit()
        }
    }

    private fun getCurrentData() {
        getCharacterService()
            .getCharacters()
            .enqueue(object : Callback<Character> {
                override fun onResponse(
                    call: Call<Character>,
                    response: Response<Character>
                ) {
                    if (response.code() in okStatusCodes) {
                        val characterResponse = response.body()!!
                        characterAdapter.submitList(characterResponse.results)
                    }
                }
                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
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