package com.jesse.rickymortyapp.screens.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.AppBarConfiguration
import com.jesse.rickymortyapp.databinding.DetailFragmentBinding
import com.jesse.rickymortyapp.network.RickMortyCharacter

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DetailFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val character = arguments?.get("character") as RickMortyCharacter

        setupRecyclerView(binding)

        binding.character = character

        val viewModelFactory = DetailViewModelFactory(requireActivity().application, character)

        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)


        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = view.findNavController()
        val appBar = AppBarConfiguration(navController.graph)
        binding.toolBar.setupWithNavController(navController, appBar)
    }

    private fun setupRecyclerView(binding: DetailFragmentBinding) {
        binding.detailRecycler.apply{
            adapter = CharacterDetailAdapter()
        }
    }

}