package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.ServiceProvider
import com.example.rickandmorty.databinding.FragmentEpisodeBinding
import kotlinx.coroutines.launch

class EpisodeFragment : Fragment() {

    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var episodeAdapter: EpisodeAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val episodeViewModel: EpisodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        initRecyclerView()
        getEpisode(sharedViewModel.linksEpisodes!!)
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerViewEpisodes.layoutManager = LinearLayoutManager(requireContext())
        episodeAdapter = EpisodeAdapter()
        binding.recyclerViewEpisodes.adapter = episodeAdapter
    }

    private fun getEpisode(list: List<String>) {
        episodeViewModel.getEpisode(list)
        episodeViewModel.episodes.observe(viewLifecycleOwner, {episodeAdapter.submitList(it)})
    }
}
