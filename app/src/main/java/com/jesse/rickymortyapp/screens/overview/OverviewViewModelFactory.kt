package com.jesse.rickymortyapp.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesse.rickymortyapp.network.RickyMortyService
import java.lang.IllegalArgumentException

class OverviewViewModelFactory(private val retrofitService: RickyMortyService):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OverviewViewModel::class.java)){
            return OverviewViewModel(retrofitService) as T
        }
        throw IllegalArgumentException("Unknown Class Exception")
    }
}