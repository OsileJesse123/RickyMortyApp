package com.jesse.rickymortyapp.screens.overview

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.jesse.rickymortyapp.databinding.OverviewFragmentBinding
import com.jesse.rickymortyapp.network.RickyMortyApi
import com.jesse.rickymortyapp.utils.CategoriesList

@RequiresApi(Build.VERSION_CODES.M)
class OverviewFragment : Fragment() {

    private val viewModelFactory = OverviewViewModelFactory(RickyMortyApi.retrofitService)
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        val binding = OverviewFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setupRecyclerView(binding)

        val categoryAdapter = binding.categoryRecycler.adapter as OverviewCategoryAdapter
        categoryAdapter.selectedItem = viewModel.currentItemPosition

        binding.executePendingBindings()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRecyclerView(binding: OverviewFragmentBinding){
        binding.categoryRecycler.apply{
            adapter = OverviewCategoryAdapter(requireContext(), CategoriesList.categories) {
                    status, itemPosition -> viewModel.setCharacters(status)
                viewModel.currentItemPosition = itemPosition
                //This is for testing purposes. You can check teh CategoriesList Object class
                //to properly understand this variable.
                CategoriesList.currentCategoryItemPosition = itemPosition
            }
        }
        binding.characterRecycler.apply{
            adapter = OverviewCharacterAdapter()
            OverviewCharacterAdapter.CharacterViewHolder.onCharacterItemClicked = {
                val action = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }
}