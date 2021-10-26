package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.App.Companion.repository
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var characterAdapter: CharacterAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val charactersViewModel: CharactersViewModel by viewModels {
        CharacterViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
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
        charactersViewModel.page++
        charactersViewModel.getDataNextPage(charactersViewModel.page)
        charactersViewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }
    }
}