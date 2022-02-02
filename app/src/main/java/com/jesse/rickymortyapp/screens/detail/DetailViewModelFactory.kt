package com.jesse.rickymortyapp.screens.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesse.rickymortyapp.network.RickMortyCharacter

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val application: Application,
                             private val character: RickMortyCharacter): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(application, character) as T
        }
        throw IllegalArgumentException("Unknown Class Exception")
    }
}