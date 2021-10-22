package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.App
import com.example.rickandmorty.App.Companion.repositoryImpl
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var charactersViewModel: CharactersViewModel
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        charactersViewModel = ViewModelProvider(
            requireActivity(),
            CharacterViewModelFactory(repositoryImpl)
        ).get(CharactersViewModel::class.java)
        charactersViewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
        initAdapter()
        binding.recyclerViewCharacters.adapter = characterAdapter
    }

    private fun initAdapter() {
        characterAdapter = CharacterAdapter ({ listLinks ->
            sharedViewModel.linksEpisodes = listLinks
            episodeFragmentTransaction()
        }, { getNextPageCharacters() }, {})
    }

    private fun episodeFragmentTransaction() {
        parentFragmentManager.
        beginTransaction()
            .replace(R.id.fragment_container, EpisodeFragment()).commit()
    }

    private fun getNextPageCharacters() {
        page++
        charactersViewModel.getDataNextPage(page)
        charactersViewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }
    }
}