package com.example.rickandmorty.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.Episode
import com.example.rickandmorty.databinding.FragmentEpisodeBinding
import com.example.rickandmorty.retrofit.RickAndMortyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASEURL = "https://rickandmortyapi.com/api/"

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private val okStatusCodes = 200..299

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initRecyclerView()
        getEpisode(sharedViewModel.linksEpisodes!!)

    }

    private fun initRecyclerView() {
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        episodeAdapter = EpisodeAdapter()
        binding.recyclerView2.adapter = episodeAdapter
    }

    private fun getEpisode(list: List<String>)  {
        val numbers = list.map { link ->
            link.filter { it.isDigit() } }.toString()
        getEpisodeService()
            .getEpisode(numbers)
            .enqueue(object : Callback<List<Episode>> {
                override fun onResponse(
                    call: Call<List<Episode>>,
                    response: Response<List<Episode>>
                ) {
                    if (response.code() in okStatusCodes) {
                        val responseEpisodes = response.body()!!
                        episodeAdapter.submitList(responseEpisodes)
                    }
                }
                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }
            })

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
