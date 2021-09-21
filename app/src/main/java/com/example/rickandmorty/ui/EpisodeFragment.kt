package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.CharacterService
import com.example.rickandmorty.data.EpisodeNW
import com.example.rickandmorty.databinding.FragmentEpisodeBinding
import com.example.rickandmorty.data.RickAndMortyService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var sharedViewModel: SharedViewModel
    //private val okStatusCodes = 200..299

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
        binding.recyclerViewEpisodes.layoutManager = LinearLayoutManager(requireContext())
        episodeAdapter = EpisodeAdapter()
        binding.recyclerViewEpisodes.adapter = episodeAdapter
    }

    private fun getEpisode(list: List<String>) {
        viewLifecycleOwner.lifecycleScope.launch {
            val numbers = list.map { link ->
                link.filter { it.isDigit() } }.toString()
            episodeAdapter.submitList(CharacterService.getCharacterService().getEpisode(numbers))
        }
    }
}
