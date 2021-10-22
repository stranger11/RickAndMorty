package com.example.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showCharacterFragment()
    }

    private fun showCharacterFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CharactersFragment())
            .commit()
    }
}